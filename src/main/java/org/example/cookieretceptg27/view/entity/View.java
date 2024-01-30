package org.example.cookieretceptg27.view.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.recipe.entity.Recipe;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name ="'view'")
public class View {
       @Id
       @GeneratedValue(strategy = GenerationType.UUID)
       private UUID id;

       @ElementCollection
       List<UUID> userId;

       @ManyToOne
       @JsonIgnore
      private Recipe recipes;


}
