package br.com.pr.sida.login.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
}
