package org.example.cookieretceptg27.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.dto.RecipeUpdateDto;
import org.example.cookieretceptg27.recipe.dto.SearchResponseDto;
import org.example.cookieretceptg27.recipe.entity.RecipeRate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeResponseDto);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<RecipeResponseDto> update(@PathVariable(name = "recipeId") UUID id,
                                                    @RequestBody RecipeUpdateDto recipeUpdateDto) {
        RecipeResponseDto recipeResponseDto = recipeService.update(id, recipeUpdateDto);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(recipeResponseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        recipeService.delete(id);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeResponseDto>>getAll()
    {
        List<RecipeResponseDto>responseDtos=recipeService.getAll();
        return ResponseEntity.ok(responseDtos);
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
    public ResponseEntity<RecipeResponseDto> createRate(@RequestBody RecipeRate recipeRate){
        RecipeResponseDto responseDto = recipeService.rateCreate(recipeRate.getRecipeId(), recipeRate.getStars());
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}

