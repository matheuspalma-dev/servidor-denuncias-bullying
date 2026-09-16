package br.com.pr.sida.municipio;

import br.com.pr.sida.municipio.dto.request.MunicipioRequestDTO;
import br.com.pr.sida.municipio.dto.response.MunicipioResponseDTO;
import br.com.pr.sida.security.tirar.xss.TirarXssService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MunicipioMapper {

    private final MunicipioRepository municipioRepository;
    private final TirarXssService tirarXssService;

    public Municipio converterDTOParaEntity(MunicipioRequestDTO municipioRequestDTO){
        Municipio municipio = new Municipio();
        municipio.setCodigoIbge(municipioRequestDTO.codigoIbge());
        municipio.setNome(tirarXss(municipioRequestDTO.nome()));
        municipio.setUf(municipioRequestDTO.uf());
        return municipio;
    }

    public MunicipioResponseDTO converterEntityEmDTO(Municipio municipio){
        MunicipioResponseDTO municipioResponseDTO = new MunicipioResponseDTO();
        municipioResponseDTO.setCodigoIbge(municipio.getCodigoIbge());
        municipioResponseDTO.setNome(municipio.getNome());
        municipioResponseDTO.setUf(municipio.getUf());

        return municipioResponseDTO;
    }

    private String tirarXss(String entrada) {
        return tirarXssService.tirarXss(entrada);
    }
}
