package br.com.pr.sida.escola;

import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/escolas")
@RequiredArgsConstructor
public class EscolaController {

    private final EscolaService escolaService;

    @GetMapping("/todas")
    public ResponseEntity<List<EscolaResponseDTO>> retornarTodasEscolas() {
        List<EscolaResponseDTO> escolas = escolaService.retornarTodasEscolas();
        return ResponseEntity.ok(escolas);
    }

    @PostMapping("/adicionar")
    public void adicionarEscola(@RequestBody EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        escolaService.adicionarEscola(escolaRequestResgisterDTO);
    }

    @GetMapping("/{escolaId}/denuncias")
    @PreAuthorize("hasAnyRole('REDE_ENSINO', 'ORGAO_COMPETENTE')")
    public ResponseEntity<List<DenunciaResponseDTO>> acessarDenuncias(@PathVariable long escolaId) {
        String emailUsuarioLogado = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<DenunciaResponseDTO> denuncias = escolaService.acessarDenuncias(emailUsuarioLogado, escolaId);
        if (denuncias == null){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return ResponseEntity.ok(denuncias);
    }
}
