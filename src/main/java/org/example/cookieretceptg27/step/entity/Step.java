package org.example.cookieretceptg27.step.entity;

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
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_order_recipe", columnNames = {"order", "recipe_id"})
        },
        indexes = {
                @Index(name = "idx_order", columnList = "order"),
                @Index(name = "idx_recipe", columnList = "recipe_id")
        }
)
public class Step {
    @Id
    private UUID id;

    private String detail;
    private String name;
    private Integer order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")  // Assuming there is a "recipe_id" column in the database
    private Recipe recipe;
}
