package org.example.cookieretceptg27.rate;

import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RateRepozitary extends JpaRepository<Rate, UUID> {
    List<Rate> findByRecipe(Recipe recipeId);
}
