package com.example.yemek_tarifi.service;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;

import java.util.List;

public interface CommentService {
    CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO);

    void deleteComment(Long commentId);

    CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO);

    List<CommentResponseDTO> getCommentsByRecipeId(Long recipeId);
}
