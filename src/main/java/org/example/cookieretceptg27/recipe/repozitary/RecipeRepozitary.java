package org.example.cookieretceptg27.recipe.repozitary;

import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RecipeRepozitary extends JpaRepository<Recipe, UUID> {

}
