package org.example.cookieretceptg27.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserSignInDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;
}