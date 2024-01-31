package org.example.cookieretceptg27.user;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.user.dto.UserBioDto;
import org.example.cookieretceptg27.user.dto.UserProfileResponseDto;
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
        UserProfileResponseDto responseDto = userService.getUserProfile(id);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @PostMapping("/follow/{followingId}")
    public ResponseEntity<?> follow(@PathVariable UUID followingId){
        userService.follow(followingId);

        return ResponseEntity.ok().body("Successfully followed");
    }

}
