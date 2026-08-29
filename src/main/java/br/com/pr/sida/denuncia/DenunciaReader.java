package br.com.pr.sida.denuncia;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DenunciaReader {

    private final DenunciaRepository denunciaRepository;

    public Denuncia buscarDenunciaPorId(Long id) {
        return denunciaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada com o ID: " + id));
    }
}
