package br.com.pr.sida.usuarios.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioResgisterRequestDTO(
        @NotBlank
        String nome,
        @NotBlank
        String cpf,
        @Email
        String email,
        @NotBlank
        String senha
) {
}
