package com.example.yemek_tarifi.service;

import com.example.yemek_tarifi.dto.RecipeRequestDTO;
import com.example.yemek_tarifi.dto.RecipeResponseDTO;
import java.util.List;
import java.util.Optional;

public interface RecipeService {

    RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO);

    List<RecipeResponseDTO> getAllRecipes();

    Optional<RecipeResponseDTO> getRecipeById(Long id);

    RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO requestDTO);

    void deleteRecipeById(Long id);

    void incrementLikeCount(Long id);
}
