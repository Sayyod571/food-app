package org.example.cookieretceptg27.following.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.cookieretceptg27.user.entity.User;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Followers {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @ManyToOne
    private User user;
    @ManyToOne(optional = false)
    private User users;


}
