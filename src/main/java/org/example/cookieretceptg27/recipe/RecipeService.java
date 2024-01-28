package org.example.cookieretceptg27.recipe;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.attachment.AttachmentRepository;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.recipe.dto.RecipeCreateDto;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AttachmentRepository attachmentRepository;
    private final ModelMapper mapper;

    @Value("${server.upload.dir}")
    private String uploadDir;

    /**
     *  kod toliq emas,  clean code qilish kere
     * @param recipeCreateDto
     * @return
     */
    @Transactional
    public RecipeResponseDto create(RecipeCreateDto recipeCreateDto, MultipartFile file) {

        UUID categoryId = recipeCreateDto.getCategoryId();
        UUID userId = recipeCreateDto.getUserId();

        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(userId))
                );
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(
                        () -> new EntityNotFoundException("category with id = %s not found".formatted(categoryId))
                );


        Attachment attachment = new Attachment();
        attachment.setFile_name(file.getOriginalFilename());
        attachment.setFileType(Objects.requireNonNull(file.getContentType()));
        attachment.setUrl(String.valueOf(Paths.get(uploadDir, file.getOriginalFilename())));
        Attachment savedRecipeAttachment = attachmentRepository.save(attachment);
        Recipe recipe = new Recipe();
        recipe.setName(recipeCreateDto.getName());
        recipe.setDuration(recipeCreateDto.getDuration());
        recipe.setUser(user);
        recipe.setCategory(category);
        recipe.setAttachment(savedRecipeAttachment);

        Recipe saved = recipeRepository.save(recipe);

        return new RecipeResponseDto(saved.getName(), saved.getDuration(), mapper.map(category, CategoryResponseDto.class),mapper.map(user, UserResponseDto.class));
    }
}
