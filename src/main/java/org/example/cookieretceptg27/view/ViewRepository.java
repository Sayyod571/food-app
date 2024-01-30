package org.example.cookieretceptg27.view;

import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.view.entity.View;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ViewRepository extends JpaRepository<View, UUID> {
    List<View>findByRecipes(Recipe recipe);

}
