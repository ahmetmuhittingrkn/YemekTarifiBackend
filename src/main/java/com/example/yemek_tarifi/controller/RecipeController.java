package com.example.yemek_tarifi.controller;

import com.example.yemek_tarifi.dto.RecipeRequestDTO;
import com.example.yemek_tarifi.dto.RecipeResponseDTO;
import com.example.yemek_tarifi.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recipes")
@RestController
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService=recipeService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<RecipeResponseDTO>> getAllRecipes() {
        List<RecipeResponseDTO> recipes = recipeService.getAllRecipes();
        return ResponseEntity.ok(recipes);
    }

    @PostMapping
    public ResponseEntity<RecipeResponseDTO> createRecipe(@Valid @RequestBody RecipeRequestDTO requestDTO) {
        RecipeResponseDTO createdRecipe = recipeService.createRecipe(requestDTO);
        // Cevap olarak 201 Created durumu ve oluşturulan tarifin bilgisini döner.
        return new ResponseEntity<>(createdRecipe, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> getRecipeById(@PathVariable Long id) {
        return recipeService.getRecipeById(id)
                .map(ResponseEntity::ok) // Eğer tarif bulunduysa, 200 OK ve tarifi döner.
                .orElse(ResponseEntity.notFound().build()); // Bulunamadıysa, 404 Not Found döner.
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> updateRecipe(@PathVariable Long id, @Valid @RequestBody RecipeRequestDTO requestDTO) {
        RecipeResponseDTO updatedRecipe = recipeService.updateRecipe(id, requestDTO);
        return ResponseEntity.ok(updatedRecipe); // Başarılı olursa 200 OK ve güncellenmiş tarifi döner.
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable Long id) {
        recipeService.deleteRecipeById(id);
        // Silme işlemi başarılı olduğunda cevabın gövdesinde bir şey olmaz. 204 No Content döner.
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeRecipe(@PathVariable Long id) {
        recipeService.incrementLikeCount(id);
        return ResponseEntity.ok().build();
    }

}
