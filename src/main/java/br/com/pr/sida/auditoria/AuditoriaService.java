package br.com.pr.sida.auditoria;

import br.com.pr.sida.auditoria.dto.AuditoriaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuditoriaService {

    private final AuditoriaRepository auditoriaRepository;
    private final AuditoriaMapper auditoriaMapper;
    private final Random random = new Random();

    public void registrarAuditoria(AuditoriaDTO auditoriaDTO){
        Auditoria auditoria = auditoriaMapper.converterDTOParaEntity(auditoriaDTO);
        auditoria.setId(gerarIdAuditoria());
        auditoriaRepository.save(auditoria);
    }

    private Long gerarIdAuditoria(){
        Long id;

        do {
            id = random.nextLong(1, Long.MAX_VALUE);
        } while (auditoriaRepository.existsById(id));

        return id;
    }
}
