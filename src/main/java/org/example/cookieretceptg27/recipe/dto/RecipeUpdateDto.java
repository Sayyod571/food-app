package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.step.dto.StepCreateDto;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeUpdateDto {

    private String name;

    private Integer duration; // in seconds

    private UUID categoryId;

    private List<IngredientCreateDto> ingredients;

    private List<StepCreateDto>steps;

}
