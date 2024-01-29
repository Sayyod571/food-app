package org.example.cookieretceptg27.review.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.user.dto.UserResponseDto;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReviewResponseDto {

    private UUID review_id;

    private UserResponseReview userResponseReview;

    private LocalDateTime created;

    private String message;

}
