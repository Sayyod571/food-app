package org.example.cookieretceptg27.ingredient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.ingredient.dto.IngredientCreateDto;
import org.example.cookieretceptg27.ingredient.dto.IngredientResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository repository;

    @Value("${server.upload.dir}")
    private String uploadDir;

    public IngredientResponseDto create(IngredientCreateDto createDto, MultipartFile file) {
        try {

            File destFile = Paths.get(uploadDir, file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            Attachment attachment = new Attachment();
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(uploadDir, file.getOriginalFilename())));

           /* attachment.setUser(user);

            Attachment saved = repository.save(attachment);

            user.setAttachment(saved);

            userRepository.save(user);

            return mapper.map(saved, AttachmentResponseDto.class);*/
        } catch (IOException e) {
            log.error("Error uploading file: {}", e.getMessage());
            throw new RuntimeException("Error uploading file", e);
        }
        return null;
    }
}
