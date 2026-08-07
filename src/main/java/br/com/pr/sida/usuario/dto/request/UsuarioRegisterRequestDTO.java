package br.com.pr.sida.usuario.dto.request;

import br.com.pr.sida.util.Genero;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record UsuarioRegisterRequestDTO(
    @NotBlank
    String nome,
    @Email
    String email,
    @NotBlank
    String senha,
    @NotBlank
    Genero genero,
    @Positive
    int anoNascimento

) {
}
