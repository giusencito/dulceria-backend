package com.example.candystore.application.jwt;


import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class jwtProvider {
    private static final Logger logger = LoggerFactory.getLogger(jwtProvider.class);


    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private int expirationDays;

    public String generateToken(String username, String email, boolean login) {
        List<String> roles = new ArrayList<>();
        if(login){
            roles.add("GOOGLE");
        }else{
            roles.add("GUEST");
        }
        return Jwts.builder()
                .setSubject(username)
                .claim("email", email)
                .claim("roles", roles)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime()+expirationDays*180000))
                .signWith(SignatureAlgorithm.HS512,secret.getBytes())
                .compact();
    }
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
    public String getEmailFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("email", String.class);
    }
    public List getRoleFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .get("roles", List.class);
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("token mal formado");
        } catch (UnsupportedJwtException e) {
            logger.error("token no soportado");
        } catch (ExpiredJwtException e) {
            logger.error("token expirado");
        } catch (IllegalArgumentException e) {
            logger.error("token vacío");
        } catch (SignatureException e) {
            logger.error("fail en la firma");
        }
        return false;
    }
}
