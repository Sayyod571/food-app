package org.example.cookieretceptg27.saved.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.user.entity.User;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Saved {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    @JsonManagedReference
    private User users;
     @JsonManagedReference
    @ManyToOne
    private Recipe recipe;

}
