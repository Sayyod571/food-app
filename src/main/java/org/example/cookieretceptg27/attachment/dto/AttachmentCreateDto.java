package org.example.cookieretceptg27.attachment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AttachmentCreateDto {

    private String file_name;

    private String fileType;

    private String url;

    private LocalDateTime uploadTime;

}
