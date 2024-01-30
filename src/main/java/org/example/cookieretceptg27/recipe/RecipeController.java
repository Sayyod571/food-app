package org.example.cookieretceptg27.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.dto.SearchResponseDto;
import org.example.cookieretceptg27.recipe.entity.RecipeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDto>getById(@PathVariable("id")UUID id)
    {
        RecipeResponseDto recipeResponseDto=recipeService.findById(id);
        return ResponseEntity.ok(recipeResponseDto);
    }
    @GetMapping("/search")
    public ResponseEntity<List<SearchResponseDto>> getSearch(Pageable pageable, @RequestParam(required = false) String predicate )
    {
        List<SearchResponseDto> responseDto = recipeService.search( pageable, predicate );
        return ResponseEntity.ok( responseDto );
    }
    @PostMapping("/rate")
    public ResponseEntity<RecipeResponseDto> createRate(@RequestBody RecipeRate recipeRate, UUID userId){
        RecipeResponseDto responseDto = recipeService.rateCreate(recipeRate.getRecipeId(), recipeRate.getStars(),  userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}

