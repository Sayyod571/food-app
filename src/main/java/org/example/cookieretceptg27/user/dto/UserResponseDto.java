package org.example.cookieretceptg27.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.example.cookieretceptg27.attachment.dto.AttachmentResponseDto;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto extends UserBaseDto{

    private UUID id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bio;

//    private AttachmentResponseDto attachment;

}
