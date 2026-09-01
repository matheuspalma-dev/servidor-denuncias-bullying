package br.com.pr.sida.status;

import br.com.pr.sida.security.service.RequerPermissao;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status-denuncia")
@RequiredArgsConstructor
public class StatusDenunciaController {

    private final StatusDenunciaService statusDenunciaService;

    @PostMapping("/atualizar-status/{denunciaId}/{statusDenunciaEnum}")
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    @RequerPermissao
    public void atualizarStatusDenuncia(@PathVariable Long denunciaId,@PathVariable StatusDenunciaEnum statusDenunciaEnum) {
        statusDenunciaService.atualizarStatusDenuncia(denunciaId, statusDenunciaEnum);
    }
}
