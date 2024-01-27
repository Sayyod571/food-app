package org.example.cookieretceptg27.step.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "unique_order_recipe", columnNames = {"order", "recipe_id"})
        }
)
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String detail;

    private String name;
    private Integer order_Number;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id")  // Assuming there is a "recipe_id" column in the database
    private Recipe recipe;
}
