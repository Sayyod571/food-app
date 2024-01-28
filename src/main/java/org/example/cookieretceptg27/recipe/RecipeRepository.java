package org.example.cookieretceptg27.recipe.repozitary;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.persistence.NamedQuery;
import lombok.ToString;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, UUID>, JpaSpecificationExecutor<Recipe> {
}
