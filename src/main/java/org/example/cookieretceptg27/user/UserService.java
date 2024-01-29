package org.example.cookieretceptg27.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.common.exception.EmailAlreadyExistException;
import org.example.cookieretceptg27.common.exception.InvalidEmailAddressException;
import org.example.cookieretceptg27.common.exception.PasswordNotMatchException;
import org.example.cookieretceptg27.common.service.GenericCrudService;
import org.example.cookieretceptg27.email.EmailCodeService;
import org.example.cookieretceptg27.email.OTPRepository;
import org.example.cookieretceptg27.email.entity.OTP;
import org.example.cookieretceptg27.user.dto.*;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericCrudService<User, UUID, UserCreateDto, UserUpdateDto, UserPatchDto, UserResponseDto>implements UserDetailsService {
    private final UserRepository repository;
    private final UserDtoMapper mapper;
    private final ModelMapper modelMapper;
    private final Class<User>entityClass = User.class;
    private final PasswordEncoder passwordEncoder;
    private final OTPRepository otpRepository;
    private final EmailCodeService emailCodeService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return repository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Bad credentials"));
    }


    @Override
    protected User save(UserCreateDto userCreateDto) {
        return repository.save(mapper.toEntity(userCreateDto));
    }

    @Override
    protected User updateEntity(UserUpdateDto userUpdateDto, User user) {
        // TODO: 25/01/2024  
        return null;
    }

    public UserResponseDto signIn(UserSignInDto userSignInDto) {
        User user = repository.findUserByEmail(userSignInDto.getEmail()).orElseThrow(() -> new BadCredentialsException("Email or password incorrect"));
        return mapper.toResponseDto(user);
    }
    @Transactional
    public UserResponseDto signUp(UserCreateDto userCreateDto) {
        String password = userCreateDto.getPassword();
        String confirmPassword = userCreateDto.getConfirmPassword();
        String email = userCreateDto.getEmail();

        if (!emailCodeService.isValid(userCreateDto.getEmail())){
            throw new InvalidEmailAddressException("%s is not valid ".formatted(email));
        }
        if (otpRepository.findById(email).isPresent()) {
            throw new EmailAlreadyExistException("%s email is not verified. Please verify it!".formatted(email));
        }
        if (repository.findUserByEmail(email).isPresent()){
            throw new EmailAlreadyExistException("User with email %s already exist".formatted(email));
        }
        if (!password.equals(confirmPassword)){
            throw new PasswordNotMatchException("Password and confirm password not matched!");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        OTP otp = modelMapper.map(userCreateDto, OTP.class);
        otpRepository.save(otp);

        emailCodeService.sendEmail(email);

        return modelMapper.map(userCreateDto, UserResponseDto.class);
    }
    @Transactional
    public void forgotPassword(String email) {
        repository
                .findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email not found"));
        emailCodeService.sendEmail(email);
    }


    public UserResponseDto forgotPasswordNewPassword(ForgotPasswordDto forgotPasswordDto) {
        String password = forgotPasswordDto.getPassword();
        String confirmPassword = forgotPasswordDto.getConfirmPassword();
        String email = forgotPasswordDto.getEmail();

        if (!password.equals(confirmPassword)){
            throw new PasswordNotMatchException("Password and confirm password not matched!");
        }

        User user = repository.findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("User not found"));

        user.setPassword(passwordEncoder.encode(forgotPasswordDto.getPassword()));

        repository.save(user);

        return modelMapper.map(user, UserResponseDto.class);
    }

}
