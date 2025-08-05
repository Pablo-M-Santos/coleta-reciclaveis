package com.pablomoreira.recycle_scheduler.Users.controllers;

import com.pablomoreira.recycle_scheduler.Users.DTOs.AuthenticationDTO;
import com.pablomoreira.recycle_scheduler.Users.DTOs.LoginResponseDTO;
import com.pablomoreira.recycle_scheduler.Users.models.UserModel;
import com.pablomoreira.recycle_scheduler.Users.repositories.UserRepository;
import com.pablomoreira.recycle_scheduler.Users.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")

    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((UserModel) auth.getPrincipal());

        UserModel user = repository.findByEmail(data.email());
        LoginResponseDTO response = new LoginResponseDTO(token, user.getName(), user.getEmail(), user.getRole());

        return ResponseEntity.ok(response);
    }
}
