package br.com.pr.sida.OrgaoCompetente;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteReader {
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;

    public OrgaoCompetente buscarPorEmailSemExcessao(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);
    }

    public OrgaoCompetente buscarPorEmail(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElseThrow(() -> new BadCredentialsException("Órgão competente não encontrado"));
    }
}
