package com.example.yemek_tarifi.dto;

import lombok.Data;

@Data
public class CommentRequestDTO {
    private Long recipeId;
    private String text;
} 