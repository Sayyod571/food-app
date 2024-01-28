package org.example.cookieretceptg27.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recipe")
@Slf4j
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @PostMapping
    public ResponseEntity<RecipeResponseDto> create(@RequestBody RecipeCreateDto recipeCreateDto) {
        RecipeResponseDto recipeResponseDto = recipeService.create(recipeCreateDto);
        /*return switch (Objects.requireNonNull(file.getContentType())) {
            case    MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
                RecipeResponseDto recipeResponseDto = recipeService.create(recipeCreateDto, file);
                yield ResponseEntity.ok(recipeResponseDto);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };*/

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeResponseDto);
    }
}

