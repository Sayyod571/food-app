package org.example.cookieretceptg27.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.following.entity.Followers;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.review.entity.Review;
import org.example.cookieretceptg27.view.entity.View;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "'user'")
public class User {
    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<View> views;

    @OneToOne
    private Attachment attachment;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)  // Updated mappedBy attribute to "user"
    private List<Review> reviews;

    @OneToMany(mappedBy = "users", fetch = FetchType.EAGER)
    private List<Followers> followers;
}
