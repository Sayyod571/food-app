package org.example.cookieretceptg27.category.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponseDto {

    private UUID id;

    private String name;

    private LocalDateTime createdAt;

    private List<Recipe>recipes;
}
