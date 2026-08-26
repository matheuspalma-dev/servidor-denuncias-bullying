package br.com.pr.sida.util.mappers;

import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
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
