package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.user.dto.UserResponseDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeResponseDto {

    private String name;

    private Integer duration; // in seconds

    private CategoryResponseDto category;

    private UserResponseDto user;
}
