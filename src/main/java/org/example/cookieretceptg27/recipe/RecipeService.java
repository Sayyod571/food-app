package org.example.cookieretceptg27.recipe;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.attachment.AttachmentRepository;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.ingredient.IngredientRepository;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.step.StepRepository;
import org.example.cookieretceptg27.step.dto.StepCreateDto;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
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
                recipeCreateDto.getName(),
                recipeCreateDto.getDuration(),
                responseDto,
                userResponseDto,
                ingredients,
                steps

        );
    }
}
