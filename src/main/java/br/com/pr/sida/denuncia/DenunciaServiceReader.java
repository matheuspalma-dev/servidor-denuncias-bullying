package br.com.pr.sida.denuncia;

import br.com.pr.sida.denuncia.dto.response.DenunciaResumoResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DenunciaServiceReader {

    private final DenunciaRepository denunciaRepository;
    private final DenunciaMapper denunciaMapper;

    public Denuncia buscarDenunciaPorId(Long id) {
        return denunciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada com o ID: " + id));
    }

    public List<Denuncia> buscarDenunciasPorEscolaId(Long escolaId) {
        return denunciaRepository.findAllById(Collections.singleton(escolaId));
    }

    public List<DenunciaResumoResponseDTO> retornarDenunciasResumo(List<Denuncia> denunciaList) {
        return denunciaMapper.retornarResumoDenunciaDTOList(denunciaList);
    }
}
