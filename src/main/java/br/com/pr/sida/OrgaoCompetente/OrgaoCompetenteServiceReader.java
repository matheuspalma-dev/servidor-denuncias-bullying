package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.OrgaoCompetente.exception.OrgaoCompetenteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteServiceReader {
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;

    public OrgaoCompetente buscarPorEmailSemExcessao(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);
    }

    public OrgaoCompetente buscarPorEmail(String email) {
        return orgaoCompetenteRepository.findByEmail(email)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Órgão competente não encontrado"));
    }

    public OrgaoCompetente buscarPorId(Long id) {
        return orgaoCompetenteRepository.findById(id)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Órgão competente não encontrado"));
    }

    public OrgaoCompetente buscarPorTipoDeUnidade(TipoOrgaoCompetente tipoOrgaoCompetente){
        return orgaoCompetenteRepository.findByTipoOrgaoCompetente(tipoOrgaoCompetente)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Órgão competente não encontrado"));
    }
}
