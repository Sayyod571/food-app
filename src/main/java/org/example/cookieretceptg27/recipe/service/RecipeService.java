package org.example.cookieretceptg27.recipe.service;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.common.rsql.SpecificationBuilder;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.recipe.repozitary.RecipeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper=new ModelMapper();
    public Page<RecipeResponseDto> search(Pageable pageable, String predicate) {
        Specification<Recipe> specification = SpecificationBuilder.build( predicate );
        if( specification == null )
        {
            return recipeRepository.findAll( pageable )
                    .map( entity -> modelMapper.map(entity, RecipeResponseDto.class));
        }
        return recipeRepository.findAll( specification, pageable )
                .map( entity -> modelMapper.map(entity, RecipeResponseDto.class));
    }

}
