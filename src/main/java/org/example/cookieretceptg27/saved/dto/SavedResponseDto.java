package org.example.cookieretceptg27.saved.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SavedResponseDto {

    private UUID id;

    private String name;

    private Integer duration; // in seconds

    private LocalDateTime createdAt;

    private CategoryResponseDto category;

    private UserResponseDto users;

    private List<Ingredient> ingredients;

    private List<Step> steps;

}
