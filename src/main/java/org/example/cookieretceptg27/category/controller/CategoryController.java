package org.example.cookieretceptg27.category.controller;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryCreateDto;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.dto.CategoryUpdateDto;
import org.example.cookieretceptg27.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/category")
@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping
    public ResponseEntity<CategoryResponseDto>create(@RequestBody CategoryCreateDto categoryCreateDto)
    {
        CategoryResponseDto categoryResponseDto=categoryService.create(categoryCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryResponseDto);
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto>update(@PathVariable("id") UUID id, @RequestBody CategoryUpdateDto categoryUpdateDto)
    {
        CategoryResponseDto categoryResponseDto=categoryService.put(id,categoryUpdateDto);
        return ResponseEntity.ok(categoryResponseDto);
    }
    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>>getAll(){
       List<CategoryResponseDto>categoryResponseDtos= categoryService.getAll();
       return ResponseEntity.ok(categoryResponseDtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto>getById(@PathVariable("id")UUID id)
    {
        CategoryResponseDto categoryResponseDto=categoryService.getById(id);
        return ResponseEntity.ok(categoryResponseDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?>delete(@PathVariable("id")UUID id)
    {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }
}
