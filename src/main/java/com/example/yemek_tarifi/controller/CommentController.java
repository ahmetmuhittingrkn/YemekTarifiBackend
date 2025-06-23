package com.example.yemek_tarifi.controller;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;
import com.example.yemek_tarifi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping
    public CommentResponseDTO addComment(@RequestBody CommentRequestDTO commentRequestDTO, Authentication authentication) {
        String username = authentication.getName();
        return commentService.addComment(commentRequestDTO, username);
    }

    @DeleteMapping("/{id}")
    public void deleteComment(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        commentService.deleteComment(id, username);
    }

    @PutMapping("/{id}")
    public CommentResponseDTO updateComment(@PathVariable Long id, @RequestBody CommentRequestDTO commentRequestDTO, Authentication authentication) {
        String username = authentication.getName();
        return commentService.updateComment(id, commentRequestDTO, username);
    }

    @GetMapping("/recipe/{recipeId}")
    public List<CommentResponseDTO> getCommentsByRecipe(@PathVariable Long recipeId) {
        return commentService.getCommentsByRecipeId(recipeId);
    }
}
