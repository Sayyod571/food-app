package org.example.cookieretceptg27.following.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.user.entity.User;

import java.util.UUID;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Followers {
    @Id
    private UUID id;
    @ManyToOne
    private User user;
    @ManyToOne(optional = false)
    private User users;


}
