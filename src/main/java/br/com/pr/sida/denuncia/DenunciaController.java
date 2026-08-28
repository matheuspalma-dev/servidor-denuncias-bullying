package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.AutorMensagem;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/denuncias")
public class DenunciaController {
    private final DenunciaService denunciaService;
    private final MensagemDenunciaService mensagemDenunciaService;

    public DenunciaController(DenunciaService denunciaService, MensagemDenunciaService mensagemDenunciaService) {
        this.denunciaService = denunciaService;
        this.mensagemDenunciaService = mensagemDenunciaService;
    }

    @PostMapping("/criar")
    public ResponseEntity<AcessoDenunciaResponseDTO> criarDenuncia(
            @RequestBody DenunciaRequestDTO denunciaRequestDTO
    )
    {
        AcessoDenunciaResponseDTO acessoDenunciaResponseDTO = denunciaService.salvarDenuncia(denunciaRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(acessoDenunciaResponseDTO);
    }

    @PostMapping("/{denunciaId}/mensagem/criar/responsavel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    public void adicionarMensagemDenunciaResponsavel(
            @RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            @PathVariable Long denunciaId
            ){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mensagemDenunciaService.salvarMensagemResponsavel(denunciaId, mensagemDenunciaRequestDTO, email);
    }

    @PostMapping("/mensagem/criar/denunciante")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('DENUNCIANTE')")
    public void adicionarMensagemDenunciaDenunciante(@RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        Long idDenuncia = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mensagemDenunciaService.salvarMensagem(idDenuncia, mensagemDenunciaRequestDTO, AutorMensagem.DENUNCIANTE);
    }
}
