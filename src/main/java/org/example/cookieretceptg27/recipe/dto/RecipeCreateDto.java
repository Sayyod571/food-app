package org.example.cookieretceptg27.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RecipeCreateDto {

    private String name;

    private Integer duration; // in seconds

    private UUID categoryId;

    private MultipartFile file;

    private UUID userId;
}
