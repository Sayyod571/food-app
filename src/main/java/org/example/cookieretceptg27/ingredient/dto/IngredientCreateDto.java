package org.example.cookieretceptg27.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.enums.Measurement;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientCreateDto {

    private String name;

    private Double quantity;

    private Measurement measurement;

    private MultipartFile attachment;

    private UUID recipeId;
}
