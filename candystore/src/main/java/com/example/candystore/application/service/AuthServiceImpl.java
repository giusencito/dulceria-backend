package com.example.candystore.application.service;

import com.example.candystore.application.dto.JwtDTO;
import com.example.candystore.application.dto.LoginDTO;
import com.example.candystore.application.jwt.jwtProvider;
import com.example.candystore.domain.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class AuthServiceImpl implements AuthService {


    @Autowired
    private jwtProvider provider;
    @Override
    public JwtDTO login(LoginDTO request) {
        String token = provider.generateToken(request.getUsername(),request.getEmail(),request.isLogin());
        JwtDTO jwtDto = new JwtDTO(token);
        return jwtDto;
    }
}
