package org.example.cookieretceptg27.category.service;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryCreateDto;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.dto.CategoryUpdateDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.category.repozitary.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper mapper=new ModelMapper();


    public CategoryResponseDto create(CategoryCreateDto categoryCreateDto) {
        Category map = mapper.map(categoryCreateDto, Category.class);
        Category save = categoryRepository.save(map);
        return mapper.map(save,CategoryResponseDto.class);
    }



    public CategoryResponseDto put(UUID id, CategoryUpdateDto categoryUpdateDto) {
        Category category = categoryRepository.findById(id).get();
        mapper.map(categoryUpdateDto,category);
        return mapper.map(category, CategoryResponseDto.class);
    }

    public List<CategoryResponseDto> getAll() {
        List<Category> all = categoryRepository.findAll();
        return all.stream().map(category -> mapper.map(category, CategoryResponseDto.class)).toList();
    }

    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    public CategoryResponseDto getById(UUID id) {
       Category byId = categoryRepository.findById(id).get();
        return mapper.map(byId, CategoryResponseDto.class);
    }
}
