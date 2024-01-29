package org.example.cookieretceptg27.step.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
                @UniqueConstraint(name = "unique_order_recipe", columnNames = {"order_number", "recipe_id"})
        }
)
public class Step {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String detail;

    private String name;

    private Integer orderNumber;

    @ManyToOne
    @JsonIgnore
    @JsonProperty("recipe_id")
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
