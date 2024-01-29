package org.example.cookieretceptg27.attachment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.UnsupportedMediaTypeStatusException;

import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/files")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService service;

    @PostMapping( value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> uploadFile(@RequestParam("file")MultipartFile file, @RequestParam("userId") UUID userId) throws IOException {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE,
                    MediaType.IMAGE_JPEG_VALUE,
                    MediaType.IMAGE_PNG_VALUE -> {
                AttachmentResponseDto attachmentResponseDto = service.processImageUpload(file, userId);
                yield ResponseEntity.ok(attachmentResponseDto);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @PutMapping( value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<AttachmentResponseDto> updateFile(@RequestParam("file")MultipartFile file, @RequestParam("usertId") UUID userId) throws IOException {
        return switch (Objects.requireNonNull(file.getContentType())) {
            case MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE -> {
                AttachmentResponseDto attachmentResponseDto = service.processImageUpdate(file, userId);
                yield ResponseEntity.ok(attachmentResponseDto);
            }
            default -> {
                log.error("Unsupported filetype: {}", file.getContentType());
                throw new UnsupportedMediaTypeStatusException(
                        String.format("Unsupported filetype: %s", file.getContentType()));
            }
        };

    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> delete( @PathVariable String userId){
        service.deleteAttachment(userId);
        return ResponseEntity.noContent().build();
    }





}
