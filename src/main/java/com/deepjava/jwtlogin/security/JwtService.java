package com.deepjava.jwtlogin.security;

import com.deepjava.jwtlogin.entity.User;

public interface JwtService {
    String generateToken(User userDetails);
    boolean validateToken(String token);
    String getUsernameFromToken(String token) ;
}