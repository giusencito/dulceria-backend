package com.example.candystore.api.controller;


import com.example.candystore.application.dto.JwtDTO;
import com.example.candystore.application.dto.LoginDTO;
import com.example.candystore.domain.service.AuthService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthService authService;


    @ApiOperation(value="login",notes = "Esta consulta nos ayuda a logear si es una cuenta de Google o Invitado")
    @PostMapping("/login")
    public JwtDTO login(@RequestBody LoginDTO request){
        return authService.login(request);
    }
}
