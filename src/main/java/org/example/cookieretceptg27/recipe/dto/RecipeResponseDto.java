package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.recipe_attachment.entity.Recipe_attachment;
import org.example.cookieretceptg27.user.entity.User;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RecipeResponseDto {
    private UUID id;
    private String name;
    private List<Recipe_attachment> recipeAttachments;
    private User user;
    private Double reviews;

}
