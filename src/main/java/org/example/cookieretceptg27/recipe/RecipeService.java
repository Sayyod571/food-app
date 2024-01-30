package org.example.cookieretceptg27.recipe;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.attachment.AttachmentRepository;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.common.rsql.SpecificationBuilder;
import org.example.cookieretceptg27.ingredient.IngredientRepository;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.rate.Rate;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.dto.SearchResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.step.StepRepository;
import org.example.cookieretceptg27.step.dto.StepCreateDto;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final  RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;
    private final AttachmentRepository attachmentRepository;
    private final StepRepository stepRepository;
    private final ModelMapper mapper;

    @Value("${server.upload.dir}")
    private String uploadDir;

    /**
     *  kod toliq emas,  clean code qilish kere
     * @param recipeCreateDto
     * @return
     */
    @Transactional
    public RecipeResponseDto create(RecipeCreateDto recipeCreateDto) {

        UUID categoryId = recipeCreateDto.getCategoryId();
        UUID userId = recipeCreateDto.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(userId))
                );
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("category with id = %s not found".formatted(categoryId))
                );
        Recipe recipe = new Recipe();
        recipe.setName(recipeCreateDto.getName());
        recipe.setDuration(recipeCreateDto.getDuration());
        recipe.setUser(user);
        recipe.setCategory(category);
        Recipe savedRecipe = recipeRepository.save(recipe);


/*        Attachment attachment = new Attachment();
        attachment.setFile_name(file.getOriginalFilename());
        attachment.setFileType(Objects.requireNonNull(file.getContentType()));
        attachment.setUrl(String.valueOf(Paths.get(uploadDir, file.getOriginalFilename())));
        Attachment savedRecipeAttachment = attachmentRepository.save(attachment);*/

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



        Recipe saved = recipeRepository.save(savedRecipe);

        CategoryResponseDto responseDto = mapper.map(category, CategoryResponseDto.class);
        UserResponseDto userResponseDto = mapper.map(user, UserResponseDto.class);

        return new RecipeResponseDto(
                saved.getId(),
                recipeCreateDto.getName(),
                recipeCreateDto.getDuration(),
                savedRecipe.getCreatedAt(),
                responseDto,
                userResponseDto,
                ingredients1,
                stepList1

        );
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
        List<Rate> rates = recipe.getRates();
        for (Rate rate : rates) {
            sum += rate.getRate();
        }
        SearchResponseDto searchResponseDto = new SearchResponseDto();
        if (sum != 0 && !rates.isEmpty()) {
            searchResponseDto.setStars(sum / rates.size());
        }
        String name = recipe.getName();
        String trueName = name.substring(0, 1).toUpperCase() + name.substring(1);
        searchResponseDto.setName(trueName);
        searchResponseDto.setRecipeAttachments(recipe.getAttachment());
        searchResponseDto.setUserName(recipe.getUser().getName());
        return searchResponseDto;
    }
}
