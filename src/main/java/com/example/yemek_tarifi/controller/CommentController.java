package com.example.yemek_tarifi.controller;

import com.example.yemek_tarifi.dto.CommentRequestDTO;
import com.example.yemek_tarifi.dto.CommentResponseDTO;
import com.example.yemek_tarifi.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommentResponseDTO> addComment(@Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO createdComment = commentService.addComment(commentRequestDTO);
        return ResponseEntity.ok(createdComment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> updateComment(@PathVariable Long id, @Valid @RequestBody CommentRequestDTO commentRequestDTO) {
        CommentResponseDTO updatedComment= commentService.updateComment(id, commentRequestDTO);
        return ResponseEntity.ok(updatedComment);
    }

    @GetMapping("/recipe/{recipeId}")
    public ResponseEntity<List<CommentResponseDTO>> getCommentsByRecipe(@PathVariable Long recipeId) {
        List<CommentResponseDTO> commentList = commentService.getCommentsByRecipeId(recipeId);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Void> likeComment(@PathVariable Long id) {
        commentService.incrementLikeCount(id);
        return ResponseEntity.ok().build();
    }
}
