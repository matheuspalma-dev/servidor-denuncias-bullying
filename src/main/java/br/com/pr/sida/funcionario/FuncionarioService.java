package br.com.pr.sida.funcionario;

import br.com.pr.sida.funcionario.dto.FuncionarioLoginRequestDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public FuncionarioService(
            FuncionarioRepository funcionarioRepository,
            PasswordEncoder passwordEncoder
    )
    {
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void login(FuncionarioLoginRequestDTO funcionarioLoginRequestDTO){
        Funcionario funcionario = funcionarioRepository.findByEmail(funcionarioLoginRequestDTO.email())
                                    .orElseThrow(() -> new EntityNotFoundException("Funcionario não encontrao no sistema"));

        if (!passwordEncoder.matches(funcionarioLoginRequestDTO.senha(), funcionario.getSennha())) {
            throw new BadCredentialsException("Informações incorretas");
        }
    }
}
