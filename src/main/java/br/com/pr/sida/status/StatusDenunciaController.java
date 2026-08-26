package br.com.pr.sida.status;

import br.com.pr.sida.util.enums.Status;
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

    @PostMapping("/atualizar-status/{denunciaId}/{status}")
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    public void atualizarStatusDenuncia(@PathVariable Long denunciaId,@PathVariable Status status) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        statusDenunciaService.atualizarStatusDenuncia(email, denunciaId, status);
    }
}
