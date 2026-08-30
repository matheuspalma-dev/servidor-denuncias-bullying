package br.com.pr.sida.login;

import br.com.pr.sida.login.dto.response.LoginResponseDTO;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {

    public LoginResponseDTO devolverLoginResponseDTO(Long id, String nome, String email) {
        LoginResponseDTO loginResponseDTO = new LoginResponseDTO();
        loginResponseDTO.setId(id);
        loginResponseDTO.setNome(nome);
        loginResponseDTO.setEmail(email);
        return loginResponseDTO;
    }
}
