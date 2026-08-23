package br.com.pr.sida.status;

import br.com.pr.sida.util.Status;
import lombok.RequiredArgsConstructor;
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
    public void atualizarStatusDenuncia(@PathVariable Long denunciaId,@PathVariable Status status) {
        statusDenunciaService.atualizarStatusDenuncia(denunciaId, status);
    }
}
