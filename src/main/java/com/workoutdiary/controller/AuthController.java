package com.workoutdiary.controller;

import com.workoutdiary.dto.AuthResponseDTO;
import com.workoutdiary.dto.SigninRequestDTO;
import com.workoutdiary.dto.SignupRequestDTO;
import com.workoutdiary.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoints de autenticação.
 * POST /auth/signup - Cadastro de novo usuário
 * POST /auth/signin - Login e geração de token JWT
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * Cadastra um novo usuário e retorna um token JWT.
     */
    @PostMapping("/signup")
    public ResponseEntity<AuthResponseDTO> signup(@Valid @RequestBody SignupRequestDTO request) {
        AuthResponseDTO response = authService.signup(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Autentica um usuário existente e retorna um token JWT.
     */
    @PostMapping("/signin")
    public ResponseEntity<AuthResponseDTO> signin(@Valid @RequestBody SigninRequestDTO request) {
        AuthResponseDTO response = authService.signin(request);
        return ResponseEntity.ok(response);
    }
}
