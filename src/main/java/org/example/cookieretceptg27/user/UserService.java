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
import org.example.cookieretceptg27.recipe.dto.RecipeResponseDto;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.user.dto.*;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    @Transactional
    public void forgotPassword(String email) {
        repository
                .findUserByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Email not found"));
        emailService.sendEmail(email);
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

    public UserProfileResponseDto getUserProfile(UUID id) {
        User user = repository
                .findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(id))
                );
        List<Recipe> recipes = user.getRecipes();

        List<RecipeResponseDto> list = recipes.stream().map(recipe -> modelMapper.map(recipe, RecipeResponseDto.class)).toList();
        List<User> users = user.getUsers();
        return UserProfileResponseDto.builder()
                .bio(user.getBio())
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .recipeCount(recipes.size())
                .followers(users.size())
                .following(0)
                .recipeResponseDto(list)
                .build();
    }

    public void follow(UUID followingId) {
        User user = repository.findById(followingId)
                .orElseThrow(
                        () -> new EntityNotFoundException("user with id = %s not found".formatted(followingId))
                );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();

        User followingUser = repository.findUserByEmail(name).orElseThrow(() ->
                new EntityNotFoundException("There is no such email user "));
        user.getUsers().add(followingUser);
    }
}
