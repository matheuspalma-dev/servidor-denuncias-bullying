package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EscolaService {
    private final EscolaRepository escolaRepository;

    public EscolaService(EscolaRepository escolaRepository) {
        this.escolaRepository = escolaRepository;
    }

    public List<EscolaResponseDTO> retornarTodasEscolas() {
        List<Escola> escolas = escolaRepository.findAll();
        List<EscolaResponseDTO> escolaResponseDTO = new ArrayList<>();

        for (Escola escola : escolas) {
            escolaResponseDTO.add(converterEntityEmDTO(escola));
        }
        return escolaResponseDTO;
    }

    private EscolaResponseDTO converterEntityEmDTO(Escola escola){
        EscolaResponseDTO escolaResponseDTO = new EscolaResponseDTO();
        escolaResponseDTO.setId(escolaResponseDTO.getId());
        escolaResponseDTO.setNome(escolaResponseDTO.getNome());
        return escolaResponseDTO;
    }
}
