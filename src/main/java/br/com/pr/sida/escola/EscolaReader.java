package br.com.pr.sida.escola;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscolaReader {

    private final EscolaRepository escolaRepository;

    public Escola buscarEscolaPorEmail(String email) {
        return escolaRepository.findByEmail(email).orElseThrow(() -> new BadCredentialsException("Escola não encontrada"));
    }

    public Escola buscarEscolaPorIdSemExcecao(Long idEscola){
        return escolaRepository.findById(idEscola).orElse(null);
    }

    public Escola buscarEscolaPorEmailSemExcecao(String email){
        return escolaRepository.findByEmail(email).orElse(null);
    }
}
