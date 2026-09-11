package br.com.pr.sida.usuarios.dto.request;

import br.com.pr.sida.security.tirar.xss.TirarXss;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioResgisterRequestDTO(
        @NotBlank
        @TirarXss
        String nome,
        @NotBlank
        @TirarXss
        String cpf,
        @Email
        @TirarXss
        String email,
        @NotBlank
        String senha
) {
}
