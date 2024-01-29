package org.example.cookieretceptg27.review.dto;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewCreateDto {

    private UUID recipeId;

    @Size(max = 200, message = "Comment length should not exceed 200 characters")
    private String comment;
}
