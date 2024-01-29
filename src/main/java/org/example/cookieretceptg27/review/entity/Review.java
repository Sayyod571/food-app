package org.example.cookieretceptg27.review.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@EntityListeners(EntityListeners.class)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String comment;

    @ManyToOne
    @JsonProperty("recipe_id")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;

    @CreationTimestamp
    private LocalDateTime createdAt;

   /* @Id
    @ManyToOne
    private User user_id;

    @Id
    @ManyToOne
    private Recipe recipe_id;
    private Double stars;
    @ManyToOne(optional = false)
    private Recipe recipes;

    @ManyToOne(optional = false)
    private User users;*/


}
