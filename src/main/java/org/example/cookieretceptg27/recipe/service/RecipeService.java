package org.example.cookieretceptg27.recipe.service;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.repozitary.RecipeRepozitary;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepozitary recipeRepozitary;

}
