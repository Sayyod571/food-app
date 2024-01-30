package org.example.cookieretceptg27.review;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.review.dto.ReviewCreateDto;
import org.example.cookieretceptg27.review.dto.ReviewResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> comment(ReviewCreateDto createDto) {
        ReviewResponseDto comment = service.comment(createDto);
        return ResponseEntity.ok(comment);
    }



}
