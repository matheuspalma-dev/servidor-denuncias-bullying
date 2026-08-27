package br.com.pr.sida.status;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaRepository;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaRepository;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.security.service.SecurityService;
import br.com.pr.sida.util.enums.Status;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatusDenunciaService {

    private final StatusDenunciaRepository statusDenunciaRepository;
    private final DenunciaRepository denunciaRepository;
    private final EscolaRepository escolaRepository;
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final SecurityService securityService;

    public void atualizarStatusDenuncia(String email, Long denunciaId, Status status) {
        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, localizarDenuncia(denunciaId));
        if (temPermissao) {
            StatusDenuncia statusDenuncia = new StatusDenuncia();
            statusDenuncia.setDataCriacao(LocalDate.now());
            statusDenuncia.setDenuncia(localizarDenuncia(denunciaId));
            statusDenuncia.setStatus(status);

            statusDenunciaRepository.save(statusDenuncia);
        }
    }

    public void adicionarStatusDenuncia(Long denunciaId, Status status) {
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
