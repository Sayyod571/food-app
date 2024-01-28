package org.example.cookieretceptg27.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.enums.Measurement;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponseDto {

    private String name;

    private Double quantity;

    private Measurement measurement;

    private RecipeResponseDto recipe;
}
