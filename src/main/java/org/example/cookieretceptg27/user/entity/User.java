package org.example.cookieretceptg27.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.following.entity.Followers;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.review.entity.Review;
import org.example.cookieretceptg27.view.entity.View;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "'user'")
@EntityListeners(EntityListeners.class)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<View> views;


    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Attachment attachment;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Recipe> recipes;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)  // Updated mappedBy attribute to "user"
    private List<Review> reviews;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Followers> followers;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
