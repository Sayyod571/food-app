package org.example.cookieretceptg27.recipe.dto;

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

    private Integer duration; // in seconds

    private LocalDateTime createdAt;

    private CategoryResponseDto category;

    private UserResponseDto user;

    private List<Ingredient>ingredients;

    private List<Step> steps;

}
