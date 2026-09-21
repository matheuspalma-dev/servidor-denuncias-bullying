package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.orgao.competente.exception.OrgaoCompetenteNaoEncontradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteServiceReader {
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;

    public OrgaoCompetente buscarPorId(Long id) {
        return orgaoCompetenteRepository.findById(id)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Órgão competente não encontrado"));
    }

    public OrgaoCompetente buscarPorTipoDeUnidade(TipoOrgaoCompetente tipoOrgaoCompetente){
        return orgaoCompetenteRepository.findByTipoOrgaoCompetente(tipoOrgaoCompetente)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Órgão competente não encontrado"));
    }

    public boolean verificarSeOrgaoCompetenteExistePorId(Long idOrgaoCompetente){
        return orgaoCompetenteRepository.existsById(idOrgaoCompetente);
    }

    public OrgaoCompetente buscarPorMunicipioETipoOrgaoCompetente(Long municipioId, TipoOrgaoCompetente tipoOrgaoCompetente){
        return orgaoCompetenteRepository.findByMunicipioMunicipioCodigoIbgeAndTipoOrgaoCompetente(municipioId, tipoOrgaoCompetente)
                .orElseThrow(() -> new OrgaoCompetenteNaoEncontradoException("Orgão Competente não encontrado para o município e tipo de órgão competente fornecidos"));
    }
}
