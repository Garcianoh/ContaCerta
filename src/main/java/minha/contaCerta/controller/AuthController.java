package minha.contaCerta.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import minha.contaCerta.Service.AuthService;
import minha.contaCerta.dto.LoginRequest;
import minha.contaCerta.dto.LoginResponse;
import minha.contaCerta.dto.LogouRequest;

@RestController 
@RequestMapping ("/auth")
public class AuthController {
    
    private final AuthService authService;

    public AuthController (AuthService authService) {
        this.authService = authService;
    }

    @PostMapping ("/login")
    public ResponseEntity<LoginResponse> login (@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping ("/refresh-token")
    public ResponseEntity<LoginResponse> refresh(@Valid @RequestBody LogouRequest request){
        return ResponseEntity.ok(authService.refresh(request));
    }

    @PostMapping ("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogouRequest request) {
        authService.logout(request);
        return ResponseEntity.noContent().build();
    }
}
