package org.example.cookieretceptg27.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class UserBioDto {

    @JsonProperty("userId")
    private UUID id;
    private String bio;
}
