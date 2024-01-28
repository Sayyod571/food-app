package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientResponseDto;
import org.example.cookieretceptg27.step.dto.StepCreateDto;
import org.example.cookieretceptg27.step.dto.StepResponseDto;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.dto.UserResponseDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDto {

    private String name;

    private Integer duration; // in seconds

    private CategoryResponseDto category;

    private UserResponseDto user;

    private List<IngredientCreateDto>ingredients;

    private List<StepCreateDto> steps;

}
