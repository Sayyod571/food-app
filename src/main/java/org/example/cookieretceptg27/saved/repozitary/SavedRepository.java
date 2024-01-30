package org.example.cookieretceptg27.saved.repozitary;

import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.saved.entity.Saved;
import org.example.cookieretceptg27.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SavedRepository extends JpaRepository<Saved, UUID> {
    Saved findByRecipeIdAndUsersId(UUID recipe_id,UUID user_id);
    List<Saved> findByUsersId(UUID usersId);
    List<Saved>findByRecipe(Recipe recipe);
}
