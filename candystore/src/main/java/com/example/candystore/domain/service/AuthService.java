package com.example.candystore.domain.service;

import com.example.candystore.application.dto.JwtDTO;
import com.example.candystore.application.dto.LoginDTO;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    JwtDTO login(LoginDTO request);
}
