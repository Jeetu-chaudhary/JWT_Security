package com.deepjava.jwtlogin.service.impl;

import com.deepjava.jwtlogin.entity.User;
import com.deepjava.jwtlogin.repository.UserRepository;
import com.deepjava.jwtlogin.service.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService, org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    // Handle OAuth2 user creation/loading
    public UserDetails loadOAuth2User(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");
        return userRepository.findByUsername(email)
                .orElseGet(() -> createNewOAuth2User(oAuth2User));
    }

    private User createNewOAuth2User(OAuth2User oAuth2User) {
        User newUser = new User();
        newUser.setUsername(oAuth2User.getAttribute("email"));
        return userRepository.save(newUser);
    }
}