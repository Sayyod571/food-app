package org.example.cookieretceptg27.user.dto;

import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto extends UserBaseDto{

    private UUID id;

}
