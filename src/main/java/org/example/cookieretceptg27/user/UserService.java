package org.example.cookieretceptg27.user;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.category.CategoryRepository;
import org.example.cookieretceptg27.category.dto.CategoryResponseDto;
import org.example.cookieretceptg27.category.entity.Category;
import org.example.cookieretceptg27.common.exception.EmailAlreadyExistException;
import org.example.cookieretceptg27.common.exception.InvalidEmailAddressException;
import org.example.cookieretceptg27.common.exception.PasswordNotMatchException;
import org.example.cookieretceptg27.common.service.GenericCrudService;
import org.example.cookieretceptg27.email.EmailCodeRepository;
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

import java.util.List;
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
    private final EmailCodeRepository emailRepository;
    private final EmailCodeService emailService;
    private final OTPRepository otpRepository;
    private final CategoryRepository categoryRepository;

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

    public UserResponseDto signIn(UserSignInDto signInDto) {
        String password = signInDto.getPassword();
        User user = repository
                .findUserByEmail(signInDto.getEmail())
                .orElseThrow(
                        () -> new BadCredentialsException("Email or password incorrect")
                );

        if( !passwordEncoder.matches( password, user.getPassword() ) )
        {
            throw new BadCredentialsException( "Email or password doesn't match" );
        }
        return mapper.toResponseDto(user);
    }
    @Transactional
    public UserResponseDto signUp(UserCreateDto userCreateDto) {
        String password = userCreateDto.getPassword();
        String confirmPassword = userCreateDto.getConfirmPassword();
        String email = userCreateDto.getEmail();

        if (!emailService.isValid(userCreateDto.getEmail())){
            throw new InvalidEmailAddressException("%s is not valid ".formatted(email));
        }
        if (emailRepository.findById(email).isPresent()) {
            throw new EmailAlreadyExistException("%s email is not verified. Please verify it!".formatted(email));
        }
        if (repository.findUserByEmail(email).isPresent()){
            throw new EmailAlreadyExistException("User with email %s already exist".formatted(email));
        }
        if (!password.equals(confirmPassword)){
            throw new PasswordNotMatchException("Password and confirm password not matched!");
        }

        userCreateDto.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

        emailService.sendEmail(email);

        OTP otp = modelMapper.map(userCreateDto, OTP.class);
        otpRepository.save(otp);

        return modelMapper.map(userCreateDto, UserResponseDto.class);
    }

    public UserHomePageResponseDto getUserHomePage(UUID id) {
        User user = repository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(id))
                );

        List<Category> categoryList = categoryRepository.findAll();
        List<CategoryResponseDto> categoryResponse = categoryList
                .stream()
                .map(category -> modelMapper.map(category, CategoryResponseDto.class))
                .toList();
        return
                UserHomePageResponseDto.builder()
                .userResponseDto(modelMapper.map(user, UserResponseDto.class))
                .categoryResponseDto(categoryResponse)
                .build();
    }

    public UserResponseDto setBio(UserBioDto bioDto) {
        UUID userId = bioDto.getId();
        User user = repository
                .findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(userId))
                );
        user.setBio(bioDto.getBio());
        User savedUse = repository.save(user);
        return mapper.toResponseDto(savedUse);
    }
}
