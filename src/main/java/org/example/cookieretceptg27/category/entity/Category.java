package org.example.cookieretceptg27.category.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Category {
    @Id
    private UUID id;
    @Column(unique = true,nullable = false)
    private String name;
    @OneToOne
    private Recipe recipe;
    @OneToOne(mappedBy = "category", optional = false)
    private Recipe recipe2;

    public Recipe getRecipe2() {
        return recipe2;
    }

    public void setRecipe2(Recipe recipe2) {
        this.recipe2 = recipe2;
    }
}
