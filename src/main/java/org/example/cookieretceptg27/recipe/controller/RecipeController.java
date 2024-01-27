package org.example.cookieretceptg27.recipe.controller;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.service.RecipeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/recipe")
@RequiredArgsConstructor
@RestController
public class RecipeController {
    private final RecipeService recipeService;
    @GetMapping()
    public ResponseEntity<Page<RecipeResponseDto>> getUsers(Pageable pageable, @RequestParam String predicate )
    {
        Page<RecipeResponseDto> responseDto = recipeService.search( pageable, predicate );
        return ResponseEntity.ok( responseDto );
    }

}
