package org.example.cookieretceptg27.recipe.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.recipe_attachment.entity.Recipe_attachment;
import org.example.cookieretceptg27.recipe_ingredient.entity.RecipeIngredients;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.entity.User;
import org.example.cookieretceptg27.review.entity.Review;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Integer duration;
    @OneToMany(mappedBy = "recipe",fetch = FetchType.LAZY)
    private List<RecipeIngredients>recipeIngredients;
    @OneToMany(mappedBy = "recipe",fetch = FetchType.LAZY)
    private List<Step>steps;
    @OneToMany(mappedBy = "recipes",fetch = FetchType.LAZY)
    private List<Recipe_attachment>recipeAttachments;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "recipes",fetch = FetchType.LAZY)
    private List<Review>reviews;
    @ManyToOne
    private Category category;

    @ManyToOne(optional = false)
    private User users;
    private LocalDateTime searchDate;

    @ManyToOne(optional = false)
    private Category categories;

}
