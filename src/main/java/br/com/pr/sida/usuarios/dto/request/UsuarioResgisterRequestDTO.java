package br.com.pr.sida.usuarios.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UsuarioResgisterRequestDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O CPF é obrigatório")
        String cpf,
        @NotBlank(message = "O email é obrigatório")
        @Email(message = "O email deve ser válido")
        String email,
        @NotBlank(message = "A senha é obrigatória")
        String senha
) {
}
