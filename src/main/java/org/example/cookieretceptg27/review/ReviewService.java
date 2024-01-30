package org.example.cookieretceptg27.review;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.RecipeRepository;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.review.dto.ReviewCreateDto;
import org.example.cookieretceptg27.review.dto.ReviewResponseDto;
import org.example.cookieretceptg27.review.dto.UserResponseReview;
import org.example.cookieretceptg27.review.entity.Review;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewService {
    ModelMapper mapper = new ModelMapper();
    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    private static int NUM = 0;

    public ReviewResponseDto comment(ReviewCreateDto createDto) {

        UUID recipeId = createDto.getRecipeId();


        Review review = mapper.map(createDto, Review.class);
        Review savedReview = repository.save(review);

        UUID id = savedReview.getId();
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setReview_id(id);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String name = authentication.getName();

            User user = userRepository.findUserByEmail(name).orElseThrow(() ->
                    new EntityNotFoundException("There is no such email user "));

            user.getReviews().add(savedReview);

            userRepository.save(user);

            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                    () -> new EntityNotFoundException("recipe not found")
            );
            recipe.getReviews().add(savedReview);
            recipeRepository.save(recipe);


            responseDto.setCreated(LocalDateTime.now());
            responseDto.setUserResponseReview(new UserResponseReview(user.getId(), user.getName(), user.getEmail()));

            String comment = savedReview.getComment();

            responseDto.setMessage(comment);

            int i = ++NUM;
            responseDto.setCommentCount(i);
            NUM = i;


        }

        return responseDto;
    }

}








