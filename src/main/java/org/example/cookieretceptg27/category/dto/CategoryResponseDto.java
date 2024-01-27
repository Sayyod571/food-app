package org.example.cookieretceptg27.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryResponseDto {
    private UUID id;
    private String name;
    private List<Recipe>recipes;
}
