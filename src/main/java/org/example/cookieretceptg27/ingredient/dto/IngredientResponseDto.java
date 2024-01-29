package org.example.cookieretceptg27.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.enums.Measurement;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IngredientResponseDto {

    private UUID id;

    private String name;

    private Double quantity;

    private Measurement measurement;

}
