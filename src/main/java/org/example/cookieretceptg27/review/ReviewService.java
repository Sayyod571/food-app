package org.example.cookieretceptg27.review;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.RecipeRepository;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.review.dto.ReviewCreateDto;
import org.example.cookieretceptg27.review.dto.ReviewMessageResponseDto;
import org.example.cookieretceptg27.review.dto.ReviewResponseDto;
import org.example.cookieretceptg27.review.dto.UserResponseReview;
import org.example.cookieretceptg27.review.entity.Review;
import org.example.cookieretceptg27.saved.entity.Saved;
import org.example.cookieretceptg27.saved.repozitary.SavedRepository;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class ReviewService {
    ModelMapper mapper = new ModelMapper();
    private final ReviewRepository repository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final SavedRepository savedRepository;

    private static int NUM = 0;

    public ReviewResponseDto comment(ReviewCreateDto createDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User user = userRepository.findUserByEmail(name).orElseThrow(() ->
                new EntityNotFoundException("There is no such email user "));
        UUID recipeId = createDto.getRecipeId();

        Review review = mapper.map(createDto, Review.class);
        review.setUsers(user);
        Review savedReview = repository.save(review);

        UUID id = savedReview.getId();
        ReviewResponseDto responseDto = new ReviewResponseDto();
        responseDto.setReview_id(id);

        if (authentication.isAuthenticated()) {

            UserResponseReview responseReview=new UserResponseReview();
            responseReview.setId(user.getId());responseReview.setName(user.getName());
            responseReview.setEmail(user.getEmail());
            user.getReviews().add(savedReview);

            userRepository.save(user);

            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(
                    () -> new EntityNotFoundException("recipe not found")
            );
            recipe.getReviews().add(savedReview);

            recipeRepository.save(recipe);


            responseDto.setCreated(LocalDateTime.now());
            responseDto.setUserResponseReview(responseReview);

            String comment = savedReview.getComment();

            responseDto.setMessage(comment);

            int i = ++NUM;


        }

        return responseDto;
    }

    public ReviewMessageResponseDto getByComment(UUID id) {
        Recipe recipe = recipeRepository.findById(id).get();
        List<Review> byRecipe1 = repository.findByRecipe(recipe);
        List<Saved> byRecipe = savedRepository.findByRecipe(recipe);
        int size = recipe.getReviews().size();
        ReviewResponseDto responseDto=new ReviewResponseDto();
        ReviewMessageResponseDto reviewMessageResponseDto=new ReviewMessageResponseDto();
        List<ReviewResponseDto>reviewResponseDtos=new ArrayList<>();
        for (Review review : byRecipe1) {
            responseDto.setUserResponseReview(mapper.map(review.getUsers(), UserResponseReview.class));
            responseDto.setReview_id(review.getId());
responseDto.setMessage(review.getComment());
responseDto.setCreated(review.getCreated());
reviewResponseDtos.add(responseDto);
responseDto=new ReviewResponseDto();
        }
        reviewMessageResponseDto.setReviewResponseDtos(reviewResponseDtos);
        reviewMessageResponseDto.setComments_number(size);
        reviewMessageResponseDto.setSaved_users(byRecipe.size());
        return reviewMessageResponseDto;
    }
}








