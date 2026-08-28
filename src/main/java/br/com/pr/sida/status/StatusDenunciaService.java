package br.com.pr.sida.status;

import br.com.pr.sida.denuncia.DenunciaService;
import br.com.pr.sida.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class StatusDenunciaService {

    private final StatusDenunciaRepository statusDenunciaRepository;
    private final DenunciaService denunciaService;
    private final SecurityService securityService;

    public void atualizarStatusDenuncia(String email, Long denunciaId, StatusDenunciaEnum status) {
        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, denunciaService.buscarDenunciaPorId(denunciaId));
        if (temPermissao) {
            br.com.pr.sida.status.StatusDenuncia statusDenuncia = new br.com.pr.sida.status.StatusDenuncia();
            statusDenuncia.setDataCriacao(LocalDate.now());
            statusDenuncia.setDenuncia(denunciaService.buscarDenunciaPorId(denunciaId));
            statusDenuncia.setStatusDenunciaEnum(status);

            statusDenunciaRepository.save(statusDenuncia);
        }
    }

    public void adicionarStatusDenuncia(Long denunciaId, StatusDenunciaEnum status) {
        br.com.pr.sida.status.StatusDenuncia statusDenuncia = new br.com.pr.sida.status.StatusDenuncia();
        statusDenuncia.setDataCriacao(LocalDate.now());
        statusDenuncia.setDenuncia(denunciaService.buscarDenunciaPorId(denunciaId));
        statusDenuncia.setStatusDenunciaEnum(status);
        statusDenunciaRepository.save(statusDenuncia);
    }

}
