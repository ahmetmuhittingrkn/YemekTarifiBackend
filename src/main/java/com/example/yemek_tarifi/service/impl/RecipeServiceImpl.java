package com.example.yemek_tarifi.service.impl;

import com.example.yemek_tarifi.dto.RecipeRequestDTO;
import com.example.yemek_tarifi.dto.RecipeResponseDTO;
import com.example.yemek_tarifi.entity.Recipe;
import com.example.yemek_tarifi.repository.RecipeRepository;
import com.example.yemek_tarifi.service.RecipeService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public RecipeServiceImpl(RecipeRepository recipeRepository,ModelMapper modelMapper) {
        this.recipeRepository = recipeRepository;
        this.modelMapper=modelMapper;
    }

    @Override
    public RecipeResponseDTO createRecipe(RecipeRequestDTO recipeRequestDTO) {
        Recipe recipe = modelMapper.map(recipeRequestDTO, Recipe.class);
        Recipe savedRecipe = recipeRepository.save(recipe);
        // Kaydedilen Entity'yi Response DTO'ya tek satırda dönüştür.
        return modelMapper.map(savedRecipe, RecipeResponseDTO.class);
    }

    @Override
    public List<RecipeResponseDTO> getAllRecipes() {
        return recipeRepository.findAll()
                .stream()
                .map(recipe -> modelMapper.map(recipe, RecipeResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<RecipeResponseDTO> getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .map(recipe -> modelMapper.map(recipe, RecipeResponseDTO.class));
    }

    @Override
    public RecipeResponseDTO updateRecipe(Long id, RecipeRequestDTO requestDTO) {
        Recipe existingRecipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        // ModelMapper gelen DTO'daki bilgileri mevcut entity üzerine yazar.
        modelMapper.map(requestDTO, existingRecipe);

        Recipe updatedRecipe = recipeRepository.save(existingRecipe);
        return modelMapper.map(updatedRecipe, RecipeResponseDTO.class);
    }

    @Override
    public void deleteRecipeById(Long id) {
        if (!recipeRepository.existsById(id)) {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }
}
