package org.example.cookieretceptg27.recipe;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.util.Objects;

@RestController
@RequestMapping("/recipe")
@Slf4j
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;
    @PostMapping(name = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)

    public ResponseEntity<?>create(/*@RequestParam("file") MultipartFile file*/
                                                   @RequestBody RecipeCreateDto recipeCreateDto){

        MultipartFile file = recipeCreateDto.getFile();
        return switch (Objects.requireNonNull(file.getContentType())) {
            case    MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
//                RecipeResponseDto recipeResponseDto = recipeService.create(recipeCreateDto, file);
                yield ResponseEntity.ok("recipeResponseDto");
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

       /* return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(recipeResponseDto);*/
    }
}
