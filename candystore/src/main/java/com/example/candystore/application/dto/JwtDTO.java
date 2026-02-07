package com.example.candystore.application.dto;

import lombok.Data;

@Data
public class JwtDTO {
    public JwtDTO(String token){
        this.token = token;
    }
    private String token;
}
