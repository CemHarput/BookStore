package com.example.bookStore.service;


import com.example.bookStore.dto.pojo.AuthenticationRequest;
import com.example.bookStore.dto.pojo.AuthenticationResponse;
import com.example.bookStore.dto.pojo.RegisterRequest;
import com.example.bookStore.exception.UserCannotBeFoundException;
import com.example.bookStore.jwt.enums.Role;
import com.example.bookStore.model.AppUser;
import com.example.bookStore.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;
    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public UserService(PasswordEncoder passwordEncoder, UserRepository userRepository, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }


    public AuthenticationResponse register(RegisterRequest request) {
        var user = AppUser.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .createdAt(new Date())
                .updateAt(null)
                .build();
        userRepository.save(user);

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword()));
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new UserCannotBeFoundException("User not found"));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
