package org.example.cookieretceptg27.user.service;


import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.common.service.GenericDtoMapper;
import org.example.cookieretceptg27.user.dto.*;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDtoMapper extends GenericDtoMapper<User, UserCreateDto, UserUpdateDto, UserResponseDto> {

    private final ModelMapper mapper;
    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return mapper.map(userCreateDto, User.class);
    }

    @Override
    public UserResponseDto toResponseDto(User user) {
        return mapper.map(user, UserResponseDto.class);
    }

    @Override
    public void update(UserUpdateDto userUpdateDto, User user) {
         mapper.map(userUpdateDto,user);
    }
}
