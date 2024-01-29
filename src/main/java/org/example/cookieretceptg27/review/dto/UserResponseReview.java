package org.example.cookieretceptg27.review.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponseReview {

    private UUID id;

    @NotBlank(message = "auth.user.name.required")
    private String name;

    @Email
    @NotBlank(message = "auth.user.email.required")
    private String email;

}
