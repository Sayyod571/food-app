package org.example.cookieretceptg27.recipe.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.ingredient.entity.Ingredient;
import org.example.cookieretceptg27.rate.Rate;
import org.example.cookieretceptg27.step.entity.Step;
import org.example.cookieretceptg27.user.entity.User;
import org.example.cookieretceptg27.review.entity.Review;
import org.example.cookieretceptg27.view.entity.View;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(EntityListeners.class)
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer duration;

    private String link;

    private LocalDate searchDate;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty("author_id")
    @JoinColumn( name = "author_id")
    @JsonIgnore
    private User user;

    @ManyToOne
    @JsonIgnore
    @JsonProperty("category_id")
    @JoinColumn(name = "category_id")
    private Category category;


    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinTable(name = "recipe_ingredients",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private List<Ingredient> ingredients;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinTable(name = "recipe_reviews",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id")
    )
    private List<Review> reviews;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @JoinTable(name = "recipe_steps",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "step_id")
    )
    private List<Step>steps;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty("attachment_id")
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @OneToMany(mappedBy = "recipes", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<View> view;

    @OneToMany
    @JsonIgnore
    @JoinTable(name = "recipe_rate",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "rate_id")
    )
    private List<Rate> rates;
}
