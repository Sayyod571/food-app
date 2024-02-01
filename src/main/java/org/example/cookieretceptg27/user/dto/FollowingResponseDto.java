package org.example.cookieretceptg27.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class FollowingResponseDto {

    private String message;

    @JsonProperty("your following user's name")
    private String name;

    @JsonProperty("your following user's bio")
    private String bio;

    @JsonProperty("your following user's recipes")
    private List<RecipeResponseDto> recipes;


}
