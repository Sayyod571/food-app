package org.example.cookieretceptg27.category.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(unique = true,nullable = false)
    private String name;
    @OneToMany(mappedBy = "categories",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
    private List<Recipe> recipes;
}
