package com.example.yemek_tarifi.dto;

import lombok.Data;

@Data
public class RecipeRequestDTO {
    private String title;
    private String description;
    private String ingredients;
    private String instructions;
    private String imageUrl;
    private Long categoryId;
}
