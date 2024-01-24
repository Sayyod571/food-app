package org.example.cookieretceptg27.category.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.recipe.entity.Recipe;

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
    @OneToOne
    private Recipe recipe;
    @OneToOne(mappedBy = "category", optional = false)
    private Recipe recipe2;


}
