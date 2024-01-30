package org.example.cookieretceptg27.saved.service;

import lombok.RequiredArgsConstructor;
import org.example.cookieretceptg27.recipe.RecipeRepository;
import org.example.cookieretceptg27.recipe.entity.Recipe;
import org.example.cookieretceptg27.saved.dto.SavedResponseDto;
import org.example.cookieretceptg27.saved.entity.Saved;
import org.example.cookieretceptg27.saved.repozitary.SavedRepository;
import org.example.cookieretceptg27.user.UserRepository;
import org.example.cookieretceptg27.user.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class SavedService {
    private final SavedRepository savedRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper mapper=new ModelMapper();

    public SavedResponseDto saved(UUID id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findUserByEmail(name).orElseThrow(() -> new BadCredentialsException("bad"));
        Recipe recipe = recipeRepository.findById(id).get();
        User user1 = userRepository.findById(user.getId()).get();
        Recipe recipe1 = recipeRepository.findById(recipe.getId()).get();
        Saved saved=new Saved();
        saved.setUsers(user1);
        saved.setRecipe(recipe1);
        Saved save = savedRepository.save(saved);
        return mapper.map(save.getRecipe(),SavedResponseDto.class);
    }

    public List<SavedResponseDto> unsaved(UUID id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userRepository.findByEmail(name);
        Saved byRecipeUuidAndUsersId = savedRepository.findByRecipeIdAndUsersId(id, user.getId());
        savedRepository.delete(byRecipeUuidAndUsersId);
        return savedRepository.findByUsersId(user.getId()).stream().map(saved ->
                mapper.map(saved.getRecipe(),SavedResponseDto.class))
                .toList();
    }

    public List<SavedResponseDto> getAll() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User byEmail = userRepository.findByEmail(name);
        List<Saved> byUsersId = savedRepository.findByUsersId(byEmail.getId());
        return byUsersId.stream().map(saved -> mapper.map(saved.getRecipe(),SavedResponseDto.class)).toList();
    }
}
