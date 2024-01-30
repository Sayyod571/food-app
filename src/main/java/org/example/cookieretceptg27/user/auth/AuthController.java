package org.example.cookieretceptg27.user.auth;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.common.jwt.JwtService;
import org.example.cookieretceptg27.email.EmailCodeService;
import org.example.cookieretceptg27.email.dto.OtpVerifyDto;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.UserService;
import org.example.cookieretceptg27.user.dto.ForgotPasswordDto;
import org.example.cookieretceptg27.user.dto.UserCreateDto;
import org.example.cookieretceptg27.user.dto.UserResponseDto;
import org.example.cookieretceptg27.user.dto.UserSignInDto;
import org.example.cookieretceptg27.user.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final EmailCodeService emailService;
    private final JwtService jwtService;

    @PostMapping("/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto ) {
        UserResponseDto userResponseDto = userService.signUp(userCreateDto);
        return ResponseEntity
                .status( HttpStatus.CREATED )
                .body( userResponseDto );
    }
    @PostMapping("/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto){
        UserResponseDto userResponseDto = userService.signIn(userSignInDto);
        String token = jwtService.generateToken(userResponseDto.getEmail());
        return ResponseEntity
                .status( HttpStatus.OK )
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .body( userResponseDto );
    }

    @PostMapping("/verify-email")
    public ResponseEntity<?> verify(@RequestBody @Valid OtpVerifyDto verifyDto){
        emailService.verifyCode(verifyDto);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( "successfully verified" );
    }

    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(@RequestParam @Email String email){
        emailService.sendEmail(email);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body( "Successfully send. Please, check your email!" );
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestParam String email){
        userService.forgotPassword(email);
        return ResponseEntity
                .status( HttpStatus.OK )
                .body("Code sent");
    }
    @PostMapping("/forgot-password/verify-email")
    public ResponseEntity<?> forgotPasswordVerifyEmail(@RequestBody @Valid OtpVerifyDto otpVerifyDto){
        UserResponseDto userResponseDto = emailService.forgotPasswordVerifyCode(otpVerifyDto);
        String token = jwtService.generateToken(userResponseDto.getEmail());
        return ResponseEntity
                .status( HttpStatus.OK )
                .header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(token))
                .body(userResponseDto);
    }

    @PostMapping("/forgot-password/new-password")
    public ResponseEntity<?> forgotPasswordNewPassword(@RequestBody @Valid ForgotPasswordDto forgotPasswordDto){
        UserResponseDto userResponseDto = userService.forgotPasswordNewPassword(forgotPasswordDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(userResponseDto);
    }






}
