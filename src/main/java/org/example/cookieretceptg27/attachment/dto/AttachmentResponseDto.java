package org.example.cookieretceptg27.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.user.entity.User;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentResponseDto {
    private UUID id;

    private String file_name;

    private String fileType;

    private String url;

    private LocalDateTime uploadTime;

    private User user;
}
