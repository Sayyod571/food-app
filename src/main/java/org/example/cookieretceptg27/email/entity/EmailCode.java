package org.example.cookieretceptg27.email.entity;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

//HagkjwYxww1kHI5v
@Data
@AllArgsConstructor
@NoArgsConstructor
@RedisHash( timeToLive = 300)
public class EmailCode
{
    @Id
    private String email;

    @Column( nullable = false )
    private String code;

    @Column( nullable = false )
    private LocalDateTime lastSentTime;

    @Column( nullable = false )
    private int sentCount;
}
