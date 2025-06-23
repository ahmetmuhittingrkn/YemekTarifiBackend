package com.example.yemek_tarifi.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentResponseDTO {
    private Long id;
    private String text;
    private String username;
    private Date createdAt;
} 