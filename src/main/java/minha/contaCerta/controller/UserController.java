package minha.contaCerta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import minha.contaCerta.Service.UserService;
import minha.contaCerta.dto.UpdateProfileRequest;
import minha.contaCerta.dto.UserCreateRequest;
import minha.contaCerta.dto.UserCreateResponse;
import minha.contaCerta.dto.UserPerfilResponse;

@RestController 
@RequestMapping ("/users")
public class UserController {
    
    private final UserService userService;

    public UserController (UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<UserCreateResponse> cadastrar (@Valid @RequestBody UserCreateRequest request) {
        UserCreateResponse response = userService.cadastrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping ("/me")
    public ResponseEntity<UserPerfilResponse> visualizarMeuPerfil (
        @AuthenticationPrincipal UserDetails userDetails) {
            UserPerfilResponse response = userService.visualizarMeuPerfil(userDetails.getUsername());
            return ResponseEntity.ok(response);
    }

    @PatchMapping ("/me")
    public ResponseEntity<UserPerfilResponse> EditarPerfil(@Valid @RequestBody UpdateProfileRequest request) {
        UserPerfilResponse response = userService.editarPerfil(request.email(), request);
        return ResponseEntity.ok(response);
    }
}
