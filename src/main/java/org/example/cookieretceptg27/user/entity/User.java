package org.example.cookieretceptg27.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.review.entity.Review;
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

    private String bio;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToMany
    @JsonIgnore
    @JoinTable(name = "following",
            joinColumns = @JoinColumn(name = "follower_id"),
            inverseJoinColumns = @JoinColumn(name = "following_id")
    )
    private List<User> users;

    /*@ManyToMany
    @JoinTable(
            name = "user_attachment",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )*/
    @OneToOne
    @JsonProperty("attachment_id")
    @JoinColumn(name = "attachment_id")
    private Attachment attachment;

    @OneToMany(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinTable(name = "user_recipes",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "recipe_id")
    )
    private List<Recipe> recipes;


    @OneToMany(mappedBy = "users",fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Review> reviews;
/*    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Recipe> recipes;
     @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<View> views;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)  // Updated mappedBy attribute to "user"
    private List<Review> reviews;

    @OneToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private List<Followers> followers;
    @ManyToMany
    @JoinTable(
            name = "recipe_attachment",
            joinColumns = @JoinColumn(name = "recipe_id"),
            inverseJoinColumns = @JoinColumn(name = "attachment_id")
    )
    private List<Attachment> attachments;*/

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
