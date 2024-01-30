package org.example.cookieretceptg27.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.entity.User;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserHomePageResponseDto {

    private UserResponseDto userResponseDto;

    private List<CategoryResponseDto> categoryResponseDto;

}
