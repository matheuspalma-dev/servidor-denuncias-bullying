package br.com.pr.sida.escola;

import br.com.pr.sida.escola.exception.EscolaNaoEncontradaException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EscolaServiceReader {

    private final EscolaRepository escolaRepository;

    public Escola buscarEscolaPorEmail(String email) {
        return escolaRepository.findByEmail(email).orElseThrow(() -> new EscolaNaoEncontradaException("Escola não encontrada"));
    }

    public Escola buscarEscolaPorIdSemExcecao(Long idEscola){
        return escolaRepository.findById(idEscola).orElse(null);
    }

    public Escola buscarEscolaPorId(Long idEscola){
        return escolaRepository.findById(idEscola)
                .orElseThrow(() -> new EscolaNaoEncontradaException("Escola não encontrada"));
    }

    public Escola buscarEscolaPorEmailSemExcecao(String email){
        return escolaRepository.findByEmail(email).orElse(null);
    }
}
