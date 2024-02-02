package org.example.cookieretceptg27.util;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import net.datafaker.Faker;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.enums.Measurement;
import org.example.cookieretceptg27.ingredient.IngredientRepository;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.recipe.RecipeRepository;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.step.StepRepository;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class MockDataGenerator {

    private final CategoryRepository categoryRepository;
    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final StepRepository stepRepository;
    private final UserRepository userRepository;


    public void generateMockRecipes(int numberOfRecipes) {
        Faker faker = new Faker();

        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName(faker.name().fullName());
            user.setEmail(faker.internet().emailAddress());
            user.setPassword(faker.internet().password());
            user.setBio(faker.lorem().sentence());
            userRepository.save(user);
        }



        List<String> foodCategories = Arrays.asList("Asian", "Chinese", "Italian", "Mexican", "Russian", "Indian");

        for (int i = 0; i < numberOfRecipes; i++) {
            String randomFoodCategory = foodCategories.get(i);

            Category category = new Category();
            category.setName(randomFoodCategory);
            Category savedCategory = categoryRepository.save(category);



            Recipe recipe = new Recipe();
            recipe.setName(faker.food().dish());
            recipe.setDuration(new Random().nextInt(100, 600));
            recipe.setCategory(savedCategory);


            Recipe saved = recipeRepository.save(recipe);

            saved.setIngredients(generateMockIngredients(faker,saved));
            saved.setSteps(generateMockSteps(faker,saved));


        }



    }

    private List<Ingredient> generateMockIngredients(Faker faker, Recipe recipe) {
        List<Ingredient> ingredients = new ArrayList<>();

        for (int i = 0; i < 5; i++) {

            Ingredient ingredient = new Ingredient();
            ingredient.setName(faker.food().ingredient());
            ingredient.setQuantity((double) new Random().nextInt(1,5));
            ingredient.setMeasurement(Measurement.KILOGRAM);
            ingredient.setRecipe(recipe);
            Ingredient save = ingredientRepository.save(ingredient);
            ingredients.add(save);
        }

        return ingredients;
    }

    private List<Step> generateMockSteps(Faker faker, Recipe recipe) {
        List<Step> steps = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            Step step = new Step();
            step.setDetail(faker.lorem().sentence());
            step.setName(faker.lorem().sentence(10));
            step.setOrderNumber(i + 1);
            step.setRecipe(recipe);
            Step save = stepRepository.save(step);
            steps.add(save);
        }

        return steps;
    }

}
