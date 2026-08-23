package br.com.pr.sida.status;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaRepository;
import br.com.pr.sida.util.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatusDenunciaService {

    private final StatusDenunciaRepository statusDenunciaRepository;
    private final DenunciaRepository denunciaRepository;

    public void atualizarStatusDenuncia(Long denunciaId, Status status) {
        StatusDenuncia statusDenuncia = new StatusDenuncia();
        statusDenuncia.setDataCriacao(LocalDate.now());
        statusDenuncia.setDenuncia(localizarDenuncia(denunciaId));
        statusDenuncia.setStatus(status);

        statusDenunciaRepository.save(statusDenuncia);
    }

    private Denuncia localizarDenuncia(Long denunciaId) {
        return denunciaRepository.findById(denunciaId)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada"));
    }
}
