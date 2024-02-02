package org.example.cookieretceptg27.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileResponseDto {
    private UUID id;

    private String email;

    private String name;

    private String bio;

    private List<RecipeResponseDto> recipeResponseDto;

    private Integer recipeCount;

    private Integer followers;

    private Integer following;

    @JsonProperty("image")
    private AttachmentResponseDto attachment;
}
