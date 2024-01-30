package org.example.cookieretceptg27.user;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.user.dto.UserBioDto;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/home/{id}")
    public ResponseEntity<UserHomePageResponseDto> homePage(@PathVariable UUID id){
        UserHomePageResponseDto homePage = userService.getUserHomePage(id);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( homePage);
    }

    @PostMapping("/user/set-bio")
    public ResponseEntity<UserResponseDto> setBio(@RequestBody UserBioDto bioDto){
        UserResponseDto userResponseDto = userService.setBio(bioDto);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }

    @GetMapping("/user/profile/{id}")
    public ResponseEntity<?> profile(@PathVariable UUID id){
        // TODO: 30/01/2024
        return null;
    }


}
