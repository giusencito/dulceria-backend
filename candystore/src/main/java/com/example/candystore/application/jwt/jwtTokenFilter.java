package com.example.candystore.application.jwt;

import com.example.candystore.application.helper.PrincipalHelper;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class jwtTokenFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(jwtTokenFilter.class);

    @Autowired
    private jwtProvider provider;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = getToken(request);
            System.out.println(token);
            if (token != null && provider.validateToken(token)) {
                logger.info("Token: {}", token);
                String username = provider.getUsernameFromToken(token);
                String email = provider.getEmailFromToken(token);
                List<String> roles = provider.getRoleFromToken(token);
                var authorities = roles.stream()
                       .map(r -> new SimpleGrantedAuthority("ROLE_" + r)).collect(Collectors.toList());
                UserDetails principal = new PrincipalHelper(username,email,authorities);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        principal, null, principal.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            }
        }catch (Exception e){
            logger.error("User Authentication cannot be set: {}", e.getMessage());
        }
        filterChain.doFilter(request,response);
    }
    private String getToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header != null && header.startsWith("Bearer"))
            return header.replace("Bearer ", "");
        return null;
    }
}
