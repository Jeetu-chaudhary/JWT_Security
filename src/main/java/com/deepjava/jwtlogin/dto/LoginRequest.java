package com.deepjava.jwtlogin.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String username;
    private String password;

    public String getUsername() {
        return username; // ✅ This is the method causing your error
    }

    public String getPassword() {
        return password; // ✅ This is the method causing your error
    }
}
