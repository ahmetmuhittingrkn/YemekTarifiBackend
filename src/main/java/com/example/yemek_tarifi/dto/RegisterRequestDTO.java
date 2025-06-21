package com.example.yemek_tarifi.dto;

import lombok.Data;

@Data
public class RegisterRequestDTO {
    private String username;
    private String email;
    private String password;
    private String profileImageUrl;
    private String bio;

}
