package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import br.com.pr.sida.municipio.Municipio;
import br.com.pr.sida.municipio.MunicipioServiceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final EscolaMapper escolaMapper;
    private final MunicipioServiceReader municipioServiceReader;

    public List<EscolaResponseDTO> retornarTodasEscolas() {
        List<Escola> escolas = escolaRepository.findAll();
        List<EscolaResponseDTO> escolaResponseDTO = new ArrayList<>();

        for (Escola escola : escolas) {
            escolaResponseDTO.add(escolaMapper.converterEntityEmDTO(escola));
        }
        return escolaResponseDTO;
    }

    public void adicionarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO){
        Municipio municipio = municipioServiceReader.buscarPorCodigoIbge(escolaRequestResgisterDTO.codigoIbgeMunicipio());
        Escola escola = escolaMapper.converterDTOEmEntity(escolaRequestResgisterDTO, municipio);
        escolaRepository.save(escola);
    }
}
