package com.example.yemek_tarifi.service.impl;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;
import com.example.yemek_tarifi.entity.Comment;
import com.example.yemek_tarifi.entity.Recipe;
import com.example.yemek_tarifi.repository.CommentRepository;
import com.example.yemek_tarifi.repository.RecipeRepository;
import com.example.yemek_tarifi.service.CommentService;
import com.example.yemek_tarifi.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, RecipeRepository recipeRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO) {
        Recipe recipe = recipeRepository.findById(commentRequestDTO.getRecipeId())
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", commentRequestDTO.getRecipeId()));
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setRecipe(recipe);
        commentRepository.save(comment);
        CommentResponseDTO response = modelMapper.map(comment, CommentResponseDTO.class);
        response.setUsername(null); // veya "Anonim"
        return response;
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", commentId));
        comment.setText(commentRequestDTO.getText());
        commentRepository.save(comment);
        CommentResponseDTO response = modelMapper.map(comment, CommentResponseDTO.class);
        response.setUsername(null); // veya "Anonim"
        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new ResourceNotFoundException("Recipe", recipeId));
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getRecipe().getId().equals(recipeId))
                .collect(Collectors.toList());
        return comments.stream().map(comment -> modelMapper.map(comment, CommentResponseDTO.class)).collect(Collectors.toList());
    }

    @Override
    public void incrementLikeCount(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", commentId)
        );
        comment.setLikeCount(comment.getLikeCount() + 1);
        commentRepository.save(comment);
    }
}
