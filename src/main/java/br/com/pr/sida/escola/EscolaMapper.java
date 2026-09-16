package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import br.com.pr.sida.municipio.Municipio;
import br.com.pr.sida.municipio.MunicipioService;
import br.com.pr.sida.orgao.competente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.security.tirar.xss.TirarXssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EscolaMapper {
    private final TirarXssService tirarXssService;
    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private final MunicipioService municipioService;

    public Escola converterDTOEmEntity(EscolaRequestResgisterDTO escolaRequestResgisterDTO, Municipio municipio) {
        Escola escola = new Escola();
        escola.setNome(tirarXss(escolaRequestResgisterDTO.nome()));
        escola.setEmail(escolaRequestResgisterDTO.email());
        escola.setAtiva(true);
        escola.setRedeEnsino(escolaRequestResgisterDTO.redeEnsino());
        escola.setOrgaoCompetente(orgaoCompetenteServiceReader.buscarPorId(escolaRequestResgisterDTO.orgaoCompetenteId()));
        escola.setMunicipio(municipio);

        return escola;
    }

    public EscolaResponseDTO converterEntityEmDTO(Escola escola){
        EscolaResponseDTO escolaResponseDTO = new EscolaResponseDTO();
        escolaResponseDTO.setId(escola.getId());
        escolaResponseDTO.setNome(escola.getNome());
        escolaResponseDTO.setRedeEnsino(escola.getRedeEnsino());
        escolaResponseDTO.setMunicipio(municipioService.devolverMunicipioResponseDTO(escola.getMunicipio()));
        return escolaResponseDTO;
    }

    private String tirarXss(String entrada) {
        return tirarXssService.tirarXss(entrada);
    }

}
