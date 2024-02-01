package org.example.cookieretceptg27.user;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.user.dto.*;
import org.example.cookieretceptg27.util.SecurityContextHolderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/homePage")
    public ResponseEntity<UserHomePageResponseDto> homePage(){
        UserHomePageResponseDto homePage = userService.getUserHomePage();
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( homePage);
    }

    @GetMapping("/user/get-all")
public ResponseEntity<Page<UserResponseDto>> getAllUser(Pageable pageable, @RequestParam( required = false ) String predicate )
{
    Page<UserResponseDto> all = userService.getAll( pageable, predicate );
    return ResponseEntity.ok( all );
}
    @PostMapping("/user/set-bio")
    public ResponseEntity<UserResponseDto> setBio(@RequestBody UserBioDto bioDto){
        UserResponseDto userResponseDto = userService.setBio(bioDto);

        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
    @PutMapping("/user")
    public ResponseEntity<UserResponseDto>update(@RequestBody UserUpdateDto userUpdateDto){
       UserResponseDto userResponseDto = userService.updateUser(userUpdateDto);
        return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
    }
    @DeleteMapping("/user/delete")
    public ResponseEntity<?>delete(){
        userService.deleteUser();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @GetMapping("/user/profile")
    public ResponseEntity<?> profile(){
        UserProfileResponseDto responseDto = userService.getUserProfile();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
    @PostMapping("/follow/{followingId}")
    public ResponseEntity<?> follow(@PathVariable UUID followingId){
        FollowingResponseDto followingResponseDto = userService.follow(followingId);
        return ResponseEntity.ok().body(followingResponseDto);
    }

    @GetMapping("/user/followers")
    public ResponseEntity<List<UserResponseDto>>getFollowers(){
        List<UserResponseDto> userResponseDtos = userService.getFollowers();
        return ResponseEntity.ok().body(userResponseDtos);
    }

}
