package com.example.yemek_tarifi.dto;

import lombok.Data;

@Data
public class RecipeResponseDTO {
    private Long id;
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private String imageUrl;
    private CategoryResponseDTO category;
}
