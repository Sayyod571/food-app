package org.example.cookieretceptg27.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto extends UserBaseDto{

    private UUID id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bio;

}
