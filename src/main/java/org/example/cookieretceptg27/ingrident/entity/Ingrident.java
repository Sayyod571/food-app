package org.example.cookieretceptg27.ingrident.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.recipe_ingredient.entity.RecipeIngredients;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Ingrident {
    @Id
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @ManyToOne
    private Recipe recipe_id;
    @OneToOne
    private Attachment attachment;
    @ManyToOne(optional = false)
    private RecipeIngredients recipeIngredients;

}
