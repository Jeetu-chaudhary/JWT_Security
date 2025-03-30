package com.deepjava.jwtlogin.dto;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String role;
}
