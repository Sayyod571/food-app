package org.example.cookieretceptg27.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserBaseDto {


    @NotBlank(message = "auth.user.name.required")
    private String name;

    @Email
    @NotBlank(message = "auth.user.email.required")
    private String email;

}
