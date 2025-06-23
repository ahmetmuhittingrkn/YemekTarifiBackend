package com.example.yemek_tarifi.service;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO, String username);

    void deleteComment(Long commentId, String username);

    CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, String username);

    List<CommentResponseDTO> getCommentsByRecipeId(Long recipeId);
}
