package com.deepjava.jwtlogin.security.impl;

import com.deepjava.jwtlogin.entity.User;
import com.deepjava.jwtlogin.repository.UserRepository;
import com.deepjava.jwtlogin.security.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class OAuth2SuccessHandler implements AuthenticationSuccessHandler {
    
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public OAuth2SuccessHandler(@Lazy PasswordEncoder passwordEncoder,JwtService jwtService,UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService=jwtService;
        this.userRepository=userRepository;
    }
    
    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException {
        // Extract user details from OAuth2 login
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String email = oAuth2User.getAttribute("email");
        String username = oAuth2User.getAttribute("name");

        Optional<User> existingUser = userRepository.findByUsername(email);
        User user;

        if (existingUser.isEmpty()) {
            user = new User();
            user.setUsername(email);
            user.setPassword(passwordEncoder.encode("oauth_dummy_password"));
            userRepository.save(user);
        } else {
            user = existingUser.get();
        }

        // Generate JWT token
        String token = jwtService.generateToken(new User(email,"")); // Use your JWT logic

        // Return token to client (e.g., in response body)
        response.setContentType("application/json");
        response.getWriter().write("{\"token\": \"" + token + "\"}");
        String redirectUrl = "http://localhost:4200/oauth/callback?token=" + token;
        response.sendRedirect(redirectUrl);
    }
}
