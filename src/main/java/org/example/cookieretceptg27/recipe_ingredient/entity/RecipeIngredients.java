package org.example.cookieretceptg27.recipe_ingredient.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.enums.Measurement;
import org.example.cookieretceptg27.ingrident.entity.Ingrident;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class RecipeIngredients {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @Column(nullable = false)
    private String quantity;

    @Enumerated(EnumType.STRING)
    private Measurement measurement;

    @ManyToOne(fetch = FetchType.EAGER)
    private Recipe recipe;

    @OneToMany(mappedBy = "recipeIngredients", fetch = FetchType.EAGER)
    private List<Ingrident> ingredients;
}