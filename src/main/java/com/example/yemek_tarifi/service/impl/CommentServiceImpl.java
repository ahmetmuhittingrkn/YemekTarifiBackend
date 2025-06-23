package com.example.yemek_tarifi.service.impl;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;
import com.example.yemek_tarifi.entity.Comment;
import com.example.yemek_tarifi.entity.Recipe;
import com.example.yemek_tarifi.entity.User;
import com.example.yemek_tarifi.repository.CommentRepository;
import com.example.yemek_tarifi.repository.RecipeRepository;
import com.example.yemek_tarifi.repository.UserRepository;
import com.example.yemek_tarifi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, RecipeRepository recipeRepository, UserRepository userRepository, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.recipeRepository = recipeRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public CommentResponseDTO addComment(CommentRequestDTO commentRequestDTO, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + username));
        Recipe recipe = recipeRepository.findById(commentRequestDTO.getRecipeId())
                .orElseThrow(() -> new RuntimeException("Tarif bulunamadı: " + commentRequestDTO.getRecipeId()));
        Comment comment = new Comment();
        comment.setText(commentRequestDTO.getText());
        comment.setRecipe(recipe);
        comment.setUser(user);
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentResponseDTO.class);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + commentId));
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Sadece kendi yorumunuzu silebilirsiniz.");
        }
        commentRepository.delete(comment);
    }

    @Override
    @Transactional
    public CommentResponseDTO updateComment(Long commentId, CommentRequestDTO commentRequestDTO, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Yorum bulunamadı: " + commentId));
        if (!comment.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Sadece kendi yorumunuzu güncelleyebilirsiniz.");
        }
        comment.setText(commentRequestDTO.getText());
        commentRepository.save(comment);
        return modelMapper.map(comment, CommentResponseDTO.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getCommentsByRecipeId(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new RuntimeException("Tarif bulunamadı: " + recipeId));
        List<Comment> comments = commentRepository.findAll()
                .stream()
                .filter(c -> c.getRecipe().getId().equals(recipeId))
                .collect(Collectors.toList());
        return comments.stream().map(comment -> modelMapper.map(comment, CommentResponseDTO.class)).collect(Collectors.toList());
    }
}
