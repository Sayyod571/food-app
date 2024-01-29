package org.example.cookieretceptg27.ingredient;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/ingredient")
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@RequestParam("ingredientImage")MultipartFile ingredientImage,
                                    @RequestBody IngredientCreateDto createDto) {
        IngredientResponseDto ingredientResponseDto = ingredientService.create(createDto,ingredientImage);
        return ResponseEntity.status(HttpStatus.CREATED).body(ingredientResponseDto);
    }


}
