package br.com.pr.sida.util.loginDTOS;

import br.com.pr.sida.util.enums.ROLE;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String email;
}
