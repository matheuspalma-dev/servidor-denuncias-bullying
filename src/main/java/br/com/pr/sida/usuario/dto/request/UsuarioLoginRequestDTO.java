package br.com.pr.sida.usuario.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UsuarioLoginRequestDTO(
        @NotBlank
        String email,
        @NotBlank
        String senha
) {
}
