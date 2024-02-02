package org.example.cookieretceptg27.recipe;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.common.rsql.SpecificationBuilder;
import org.example.cookieretceptg27.ingredient.IngredientRepository;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.rate.Rate;
import org.example.cookieretceptg27.rate.RateRepository;
import org.example.cookieretceptg27.recipe.dto.*;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.step.StepRepository;
import org.example.cookieretceptg27.step.dto.StepCreateDto;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.entity.User;
import org.example.cookieretceptg27.util.SecurityContextHolderService;
import org.example.cookieretceptg27.view.ViewRepository;
import org.example.cookieretceptg27.view.entity.View;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import static org.example.cookieretceptg27.util.SecurityContextHolderService.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final StepRepository stepRepository;
    private final ModelMapper mapper;
    private final ViewRepository viewRepository;
    private final RateRepository rateRepository;

    @Value("${service.upload.dir}")
    private String uploadDir;

    @Transactional
    public RecipeResponseDto create(RecipeCreateDto recipeCreateDto) {

        UUID categoryId = recipeCreateDto.getCategoryId();
        User user = getUser();


        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("category with id = %s not found".formatted(categoryId))
                );
        Recipe recipe = new Recipe();
        recipe.setName(recipeCreateDto.getName().toLowerCase());
        recipe.setDuration(recipeCreateDto.getDuration());
        recipe.setUser(user);
        recipe.setCategory(category);
        recipe.setCreatedAt(LocalDateTime.now());
        Recipe savedRecipe = recipeRepository.save(recipe);

        List<IngredientCreateDto> ingredients = recipeCreateDto.getIngredients();
        List<StepCreateDto> steps = recipeCreateDto.getSteps();

        List<Ingredient> ingredientList = ingredients.stream()
                .map(ingredientCreateDto -> mapper.map(ingredientCreateDto, Ingredient.class))
                .peek(ingredient -> ingredient.setRecipe(savedRecipe))
                .toList();
        List<Ingredient> ingredients1 = ingredientRepository.saveAll(ingredientList);
        List<Step> stepList = steps.stream().map(stepCreateDto -> mapper.map(stepCreateDto, Step.class))
                .peek(step -> step.setRecipe(savedRecipe))
                .toList();
        List<Step> stepList1 = stepRepository.saveAll(stepList);

        savedRecipe.setIngredients(ingredients1);
        savedRecipe.setSteps(stepList1);
        savedRecipe.setLink("https://food-app-production-5c74.up.railway.app/recipe/"+savedRecipe.getId());


        Recipe saved = recipeRepository.save(savedRecipe);
        category.getRecipes().add(saved);
        categoryRepository.save(category);
        user.getRecipes().add(saved);
        userRepository.save(user);
        RecipeCategoryDto responseDto = mapper.map(category, RecipeCategoryDto.class);
        UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);

        return new RecipeResponseDto(
                saved.getId(),
                recipeCreateDto.getName(),
                recipeCreateDto.getDuration(),
                savedRecipe.getCreatedAt(),
                responseDto,
                userResponseDto,
                ingredients1,
                stepList1,
                0,
                0,
                saved.getLink()

        );
    }
    public RecipeResponseDto update(UUID recipeId, RecipeUpdateDto recipeUpdateDto) {
        Recipe recipe = recipeRepository
                .findById(recipeId)
                .orElseThrow(
                        () -> new EntityNotFoundException("recipe with id=%s not found".formatted(recipeId))
                );

        UUID updateDtoCategoryId = recipeUpdateDto.getCategoryId();
        Category originalCategory = recipe.getCategory();

        if (!originalCategory.getId().equals(updateDtoCategoryId)){
            Category category = categoryRepository
                    .findById(updateDtoCategoryId)
                    .orElseThrow(
                            () -> new EntityNotFoundException("updating category id=%s not found".formatted(updateDtoCategoryId))
                    );
            recipe.setCategory(category);
        }

        mapper.map(recipeUpdateDto,recipe);
        Recipe saved = recipeRepository.save(recipe);
        return mapper.map(saved, RecipeResponseDto.class);
    }

    public RecipeResponseDto findById(UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User byEmail = userRepository.findByEmail(name);
        Recipe recipe = recipeRepository.findById(id).get();

        if (viewsUser(byEmail, viewRepository.findByRecipes(recipe))) {
            RecipeResponseDto map = mapper.map(recipe, RecipeResponseDto.class);
            map.setReviews_size(recipe.getView().size());
            return map;
        } else {
            View view = new View(UUID.randomUUID(), List.of(byEmail.getId()), recipe);
            View save = viewRepository.save(view);
            recipe.getView().add(save);
            RecipeResponseDto map = new RecipeResponseDto();
            mapper.map(recipe, map);
            map.setReviews_size(recipe.getView().size());
            recipeRepository.save(recipe);
            return map;

        }

    }

    private boolean viewsUser(User byEmail, List<View> byRecipes) {
        for (View byRecipe : byRecipes) {
            for (UUID uuid : byRecipe.getUserId()) {
                if (uuid.equals(byEmail.getId())) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<SearchResponseDto> search(Pageable pageable, String predicate) {
        List<SearchResponseDto> responseDtoList = new ArrayList<>();
        if (predicate == null) {
            List<Recipe> allRecipe = recipeRepository.findAll();
            for (Recipe recipe : allRecipe) {
                if (recipe.getSearchDate() != null) {
                    SearchResponseDto searchResponseDto = getSearchResponseDto(recipe);
                    responseDtoList.add(searchResponseDto);
                }
            }
        } else {
            predicate = predicate.toLowerCase();
            Specification<Recipe> specification = SpecificationBuilder.build(predicate);
            Page<Recipe> recipes = recipeRepository.findAll(specification, pageable);
            for (Recipe recipe : recipes) {
                SearchResponseDto searchResponseDto = getSearchResponseDto(recipe);
                responseDtoList.add(searchResponseDto);
            }
        }
        return responseDtoList;
    }

    private static SearchResponseDto getSearchResponseDto(Recipe recipe) {
        double sum = 0;
        double stars = 0;
        List<Rate> rates = recipe.getRates();
        for (Rate rate : rates) {
            sum += rate.getRate();
        }
        if (sum != 0) {
            stars = sum / rates.size();
        }
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        String name = recipe.getName();
        String responseName = name.substring(0, 1).toUpperCase() + name.substring(1);
        searchResponseDto.setId(recipe.getId());
        searchResponseDto.setName(responseName);
        searchResponseDto.setStars(stars);
        searchResponseDto.setRecipeAttachments(recipe.getAttachment());
        searchResponseDto.setUserName(recipe.getUser().getName());
        return searchResponseDto;
    }

    public RecipeResponseDto rateCreate(UUID recipeId, double rates) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User user = userRepository.findUserByEmail(name).get();

        Rate rate = new Rate(UUID.randomUUID(), rates, user);
        rateRepository.save(rate);
        Recipe recipe = recipeRepository.findById(recipeId).get();
        List<Rate> rateList = recipe.getRates();
        rateList.add(rate);
        recipeRepository.save(recipe);
        double sum = 0;
        double stars = 0;
        for (Rate star : rateList) {
            sum += star.getRate();
        }
        if (sum != 0) {
            stars = sum / rateList.size();
        }
        RecipeCategoryDto responseDto = mapper.map(recipe.getCategory(), RecipeCategoryDto.class);
        UserResponseDto userResponseDto = mapper.map(recipe.getUser(), UserResponseDto.class);
        return new RecipeResponseDto(
                recipe.getId(),
                recipe.getName(),
                recipe.getDuration(),
                recipe.getCreatedAt(),
                responseDto,
                userResponseDto,
                recipe.getIngredients(),
                recipe.getSteps(),
                recipe.getReviews().size(),
                stars,
                recipe.getLink()
        );
    }

    public List<RecipeResponseDto> getAll() {
        return recipeRepository.findAll()
                .stream().
                map(recipe -> mapper.map(recipe,RecipeResponseDto.class)).toList();
    }

    public User getUser(){
        String email = getUserFromSecurityContextHolder();
        return userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    public void delete(UUID id) {
        String email = getUserFromSecurityContextHolder();
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new EntityNotFoundException("recipe author not found"));

        Recipe recipe = recipeRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("recipe with id = %s not found".formatted(id)));

        if (!recipe.getUser().getId().equals(user.getId())){
            throw new AccessDeniedException("You cannot delete this recipe. Only recipe author have access to delete");
        }

        recipeRepository.deleteById(recipe.getId());
    }

    public RecipeResponseDto getByLink(UUID id) {
        Recipe recipe = recipeRepository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("%s recipe not found".formatted(id))
                );
        return mapper.map(recipe, RecipeResponseDto.class);
    }

    public String getRecipeLink(UUID recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new EntityNotFoundException("recipe not found"));
        return  recipe.getLink();
    }
}
