package com.example.candystore.application.dto;

import lombok.Data;

@Data
public class LoginDTO {

    private String username;

    private String email;

    private boolean login;
}
