package org.example.cookieretceptg27.recipe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientResponseDto;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.step.dto.StepCreateDto;
import org.example.cookieretceptg27.step.dto.StepResponseDto;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDto {

    private UUID id;

    private String name;

    private Integer duration;

    private LocalDateTime createdAt;

    private CategoryResponseDto category;

    @JsonProperty("recipe_author")
    private UserResponseDto user;


    private List<Ingredient>ingredients;

    private List<Step> steps;

    private int reviews_size;

    private double stars;

}
