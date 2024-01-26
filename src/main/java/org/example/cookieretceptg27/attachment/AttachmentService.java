package org.example.cookieretceptg27.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;
import org.example.cookieretceptg27.attachment.entity.Attachment;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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
    private final ModelMapper mapper;

    @Value("${server.upload.dir}")
    private String uploadDir;

    public AttachmentResponseDto processImageUpload(MultipartFile file, UUID userId) throws IOException {
        if (file.isEmpty()) {
            log.error("Empty file uploaded");
            throw new IllegalArgumentException("Empty file uploaded");
        }


        try {
            File destFile = Paths.get(uploadDir, file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Attachment attachment = new Attachment();
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(uploadDir, file.getOriginalFilename())));
            attachment.setUploadTime(LocalDateTime.now());
            attachment.setUser(user);

            Attachment saved = repository.save(attachment);

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

        try{
            File destFile = Paths.get(uploadDir, file.getOriginalFilename()).toFile();
            file.transferTo(destFile);
            log.info("Uploaded: {}", destFile);

            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            Attachment attachment = user.getAttachment();
            String url = attachment.getUrl();
            deleteFile(url);

            attachment.setId(user.getAttachment().getId());
            attachment.setFile_name(file.getOriginalFilename());
            attachment.setFileType(Objects.requireNonNull(file.getContentType()));
            attachment.setUrl(String.valueOf(Paths.get(uploadDir, file.getOriginalFilename())));
            attachment.setUploadTime(LocalDateTime.now());
            attachment.setUser(user);

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


    public void deleteAttachment(UUID userId) {
        Attachment attachment = repository.findByUserId(userId).orElseThrow(() -> new AttachmentNotFound("Could not find attachment"));
        repository.deleteById(attachment.getId());
        deleteFile(attachment.getUrl());

    }
}
