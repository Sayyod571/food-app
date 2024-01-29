package org.example.cookieretceptg27.rate;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.cookieretceptg27.user.entity.User;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(columnDefinition = "integer CHECK (rate >= 1 AND rate <= 5)")
    private Integer rate;

    @OneToOne
    @JsonProperty("user_id")
    @JoinColumn(name = "user_id")
    private User user;
}
