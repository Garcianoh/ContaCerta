package minha.contaCerta.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import minha.contaCerta.Service.RecuperarSenhaService;
import minha.contaCerta.dto.RedefinirSenhaRequest;
import minha.contaCerta.dto.SolicitarRedefinicaoSenhaRequest;
import minha.contaCerta.dto.ValidarCodigoRequest;
import minha.contaCerta.dto.ValidarCodigoResponse;

@RestController 
@RequestMapping ("/auth")
public class RecuperarSenhaController {
    
    private final RecuperarSenhaService recuperarSenhaService;

    public RecuperarSenhaController (RecuperarSenhaService recuperarSenhaService) {
        this.recuperarSenhaService = recuperarSenhaService;
    }

    @PostMapping ("/forgot-password")
    public ResponseEntity<String> solicitarRecuperacao(@Valid @RequestBody SolicitarRedefinicaoSenhaRequest request) {
        return ResponseEntity.ok(recuperarSenhaService.solicitarRecuperacao(request));
    }

    @PostMapping ("/validate-code")
    public ResponseEntity<ValidarCodigoResponse> validarCodigo (@Valid @RequestBody ValidarCodigoRequest request) {
        return ResponseEntity.ok(recuperarSenhaService.validarCodigo(request));
    }

    @PatchMapping ("/reset-password")
    public ResponseEntity<String> redefinirSenha(@Valid @RequestBody RedefinirSenhaRequest request) {
        return ResponseEntity.ok(recuperarSenhaService.confirmarRedefinicaoSenha(request));
    }
}
