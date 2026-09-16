package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.municipio.Municipio;
import br.com.pr.sida.municipio.MunicipioServiceReader;
import br.com.pr.sida.orgao.competente.dto.request.OrgaoCompetenteRegisterDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final OrgaoCompetenteMapper orgaoCompetenteMapper;
    private final MunicipioServiceReader municipioServiceReader;

    public void registrarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO){
        Municipio municipio = municipioServiceReader.buscarPorCodigoIbge(orgaoCompetenteRegisterDTO.codigoIbgeMunicipio());
        OrgaoCompetente orgaoCompetente = orgaoCompetenteMapper.converterDTOEmEntity(orgaoCompetenteRegisterDTO, municipio);
        orgaoCompetenteRepository.save(orgaoCompetente);
    }

}
