package org.example.cookieretceptg27.review;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.review.dto.ReviewCreateDto;
import org.example.cookieretceptg27.review.dto.ReviewMessageResponseDto;
import org.example.cookieretceptg27.review.dto.ReviewResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/review")
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService service;

    @PostMapping
    public ResponseEntity<ReviewResponseDto> comment(ReviewCreateDto createDto) {
        ReviewResponseDto comment = service.comment(createDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(comment);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ReviewMessageResponseDto>getByComment(@PathVariable("id")UUID id)
    {
        ReviewMessageResponseDto reviewMessageResponseDto=service.getByComment(id);
        return ResponseEntity.ok(reviewMessageResponseDto);
    }

}
