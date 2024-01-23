package org.example.cookieretceptg27.recipe.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.recipe_attachment.entity.Recipe_attachment;
import org.example.cookieretceptg27.recipe_ingredient.entity.RecipeIngredient;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.entity.User;
import org.example.cookieretceptg27.review.entity.Review;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "'recipe'")
public class Recipe {
    @Id
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer duration;
    @OneToMany(mappedBy = "recipe",fetch = FetchType.EAGER)
    private List<RecipeIngredient>recipeIngredients;
    @OneToMany(mappedBy = "recipe",fetch = FetchType.EAGER)
    private List<Step>steps;
    @OneToMany(mappedBy = "recipe_id",fetch = FetchType.EAGER)
    private List<Recipe_attachment>recipeAttachments;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @OneToMany(mappedBy = "recipe_id",fetch = FetchType.EAGER)
    private List<Review>reviews;
    @OneToOne(mappedBy = "recipe")
    private Category category;

}
