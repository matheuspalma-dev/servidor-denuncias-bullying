package br.com.pr.sida.municipio;

import br.com.pr.sida.municipio.dto.request.MunicipioRequestDTO;
import br.com.pr.sida.municipio.dto.response.MunicipioResponseDTO;
import br.com.pr.sida.municipio.exception.MunicipioJaCadastradoException;
import br.com.pr.sida.municipio.exception.MunicipioNaoFoiCadastradoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MunicipioService {

    private final MunicipioRepository municipioRepository;
    private final MunicipioMapper municipioMapper;

    public void cadastrarMunicipio(MunicipioRequestDTO municipioRequestDTO) {

        if (municipioRepository.existsById(municipioRequestDTO.codigoIbge())){
            Municipio municipio = municipioRepository.findById(municipioRequestDTO.codigoIbge())
                    .orElseThrow(() -> new MunicipioNaoFoiCadastradoException("Esse municipio não foi cadastrado ou não existe"));

            if (municipio.getNome().equals(municipioRequestDTO.nome())){
                throw new MunicipioJaCadastradoException("Esse municipio já foi cadastrado");
            }

            throw new MunicipioJaCadastradoException("Esse id já foi cadastrado em um municipio");
        }

        Municipio municipio = municipioMapper.converterDTOParaEntity(municipioRequestDTO);
        municipioRepository.save(municipio);
    }

    public MunicipioResponseDTO devolverMunicipioResponseDTO(Municipio municipio){
        return municipioMapper.converterEntityEmDTO(municipio);
    }
}
