package org.example.cookieretceptg27.ingredient.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.enums.Measurement;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Ingredient {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double quantity;

    @Enumerated(EnumType.STRING)
    private Measurement measurement;

    @OneToOne
    @JoinColumn(name = "attachmentId")
    @JsonProperty("attachmentId")
    private Attachment attachment;

    @ManyToOne
    @JsonIgnore
    @JsonProperty("recipeId")
    @JoinColumn(name = "recipeId")
    private Recipe recipe;


}
