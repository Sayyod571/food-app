package org.example.cookieretceptg27.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReviewMessageResponseDto {
    private List<ReviewResponseDto>reviewResponseDtos;
    private double comments_number;
    private double saved_users;
}
