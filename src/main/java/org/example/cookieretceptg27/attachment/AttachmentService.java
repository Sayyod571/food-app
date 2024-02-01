package org.example.cookieretceptg27.attachment;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.attachment.dto.AttachmentCreateDto;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.recipe.RecipeRepository;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentService {
    private final AttachmentRepository repository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    private final ModelMapper mapper;

    @Value("${service.upload.dir}")
    private String uploadDir;



    public AttachmentResponseDto processImageUpload(MultipartFile file, UUID userId) throws IOException {

        if (file.isEmpty()) {
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new EntityNotFoundException("User with id = %s not found".formatted(userId)));


            Path directory = Paths.get(uploadDir+"/img/userImg");

            if(!Files.exists(directory)){
                Path directories = Files.createDirectories(directory);

                File destFile = Paths.get(String.valueOf(directories), file.getOriginalFilename()).toFile();
                file.transferTo(destFile);
                log.info("Uploaded: {}", destFile);

                AttachmentCreateDto attachment = new AttachmentCreateDto();
                attachment.setFile_name(file.getOriginalFilename());
                attachment.setFileType(Objects.requireNonNull(file.getContentType()));
                attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));

                Attachment map = mapper.map(attachment, Attachment.class);

                Attachment saved = repository.save(map);

                user.setAttachment(saved);

                userRepository.save(user);

                return mapper.map(saved, AttachmentResponseDto.class);
            }

            File destFile = Paths.get(String.valueOf(directory), file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            AttachmentCreateDto attachment = new AttachmentCreateDto();
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));

            Attachment map = mapper.map(attachment, Attachment.class);

            Attachment saved = repository.save(map);

            user.setAttachment(saved);

            userRepository.save(user);

            return mapper.map(saved, AttachmentResponseDto.class);
        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }


    public AttachmentResponseDto processImageUpdate(MultipartFile file, UUID userId) {
        if (file.isEmpty()) {
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }

        Path directory = Paths.get(uploadDir+"/img/userImg");

        try{
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));


            Attachment attachment = user.getAttachment();
            String url = attachment.getUrl();
            deleteFile(url);

            File destFile = Paths.get(String.valueOf(directory), file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            attachment.setId(user.getAttachment().getId());
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));

            Attachment saved = repository.save(attachment);

            return mapper.map(saved, AttachmentResponseDto.class);

        } catch (IOException e){
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }

    private void deleteFile(String filePath) {
        try {
            Files.deleteIfExists(Paths.get(filePath));
        } catch (IOException e) {
            log.error("Error deleting file: {}", e.getMessage());
            throw new RuntimeException("Error deleting file", e);
        }
    }

    public AttachmentResponseDto recipeImageUpload(MultipartFile file, UUID recipeId) {
        if (file.isEmpty()) {
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }

        try {
            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new EntityNotFoundException("Recipe with id = %s not found".formatted(recipeId)));

            Path directory = Paths.get(uploadDir+"/img/recipeImg");

            if(!Files.exists(directory)){
                Path directories = Files.createDirectories(directory);

                File destFile = Paths.get(String.valueOf(directories), file.getOriginalFilename()).toFile();
                file.transferTo(destFile);
                log.info("Uploaded: {}", destFile);

                AttachmentCreateDto attachment = new AttachmentCreateDto();
                attachment.setFile_name(file.getOriginalFilename());
                attachment.setFileType(Objects.requireNonNull(file.getContentType()));
                attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));

                Attachment saved = repository.save(mapper.map(attachment, Attachment.class));


                recipe.setAttachment(saved);

                recipeRepository.save(recipe);

                return mapper.map(attachment, AttachmentResponseDto.class);
            }

            File destFile = Paths.get(String.valueOf(directory), file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            AttachmentCreateDto attachment = new AttachmentCreateDto();
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));

            Attachment saved = repository.save(mapper.map(attachment, Attachment.class));


            recipe.setAttachment(saved);

            recipeRepository.save(recipe);

            return mapper.map(attachment, AttachmentResponseDto.class);
        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }

    public AttachmentResponseDto recipeImageUpdate(MultipartFile file, UUID recipeId) {
        if (file.isEmpty()){
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }

        Path directory = Paths.get(uploadDir+"/img/recipeImg");


        try{
            File destFile = Paths.get(String.valueOf(directory), file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            Recipe recipe = recipeRepository.findById(recipeId)
                    .orElseThrow(() -> new RuntimeException("Recipe not found"));

            Attachment attachment = recipe.getAttachment();
            String url = attachment.getUrl();
            deleteFile(url);

            attachment.setId(recipe.getAttachment().getId());
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(String.valueOf(directory), file.getOriginalFilename())));
            attachment.setUploadTime(LocalDateTime.now());

            Attachment saved = repository.save(attachment);

            return mapper.map(saved, AttachmentResponseDto.class);


        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
    }


    public void deleteAttachment(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        // Get the attachment associated with the user
        Attachment attachment = user.getAttachment();

        if (attachment != null) {
            // Remove the reference to the attachment from the user
            user.setAttachment(null);
            userRepository.save(user);

            // Delete the attachment and corresponding file
            UUID attachmentID = attachment.getId();
            repository.deleteById(attachmentID);
            deleteFile(attachment.getUrl());
        } else {
            throw new AttachmentNotFound("Attachment not found for the user");
        }
    }
}
