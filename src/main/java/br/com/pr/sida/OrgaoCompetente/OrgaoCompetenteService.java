package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.dto.request.OrgaoCompetenteLoginRequestDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final PasswordEncoder passwordEncoder;

    public OrgaoCompetenteService(
            OrgaoCompetenteRepository orgaoCompetenteRepository,
            PasswordEncoder passwordEncoder
    )
    {
        this.orgaoCompetenteRepository = orgaoCompetenteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void login(OrgaoCompetenteLoginRequestDTO orgaoCompetenteLoginRequestDTO){
        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(orgaoCompetenteLoginRequestDTO.email())
                                            .orElseThrow(() -> new RuntimeException("Orgao Competente not found"));

        if (!passwordEncoder.matches(orgaoCompetenteLoginRequestDTO.senha(), orgaoCompetente.getSenha())){
            throw new BadCredentialsException("Email ou senha inválidos");
        }
    }
}
