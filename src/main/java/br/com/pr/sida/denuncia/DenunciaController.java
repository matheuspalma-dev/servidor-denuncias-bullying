package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.AcessoDenunciaService;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.AutorMensagem;
import br.com.pr.sida.security.service.RequerPermissao;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/denuncias")
@RequiredArgsConstructor
public class DenunciaController {

    private final DenunciaService denunciaService;
    private final MensagemDenunciaService mensagemDenunciaService;
    private final AcessoDenunciaService acessoDenunciaService;


    @PostMapping("/criar")
    public ResponseEntity<AcessoDenunciaResponseDTO> criarDenuncia(
            @RequestBody @Valid DenunciaRequestDTO denunciaRequestDTO
    )
    {
        Denuncia denuncia = denunciaService.salvarDenuncia(denunciaRequestDTO);
        AcessoDenunciaResponseDTO acessoDenunciaResponseDTO = acessoDenunciaService.salvarAcessoDenuncia(denuncia);
        return ResponseEntity.status(HttpStatus.CREATED).body(acessoDenunciaResponseDTO);
    }

    @PostMapping("/{denunciaId}/mensagem/criar/responsavel")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ORGAO_COMPETENTE', 'REDE_ENSINO')")
    @RequerPermissao
    public void adicionarMensagemDenunciaResponsavel(
            @RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            @PathVariable Long denunciaId
            ){
        mensagemDenunciaService.salvarMensagemResponsavel(denunciaId, mensagemDenunciaRequestDTO);
    }

    @PostMapping("/mensagem/criar/denunciante")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('DENUNCIANTE')")
    public void adicionarMensagemDenunciaDenunciante(@RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        Long idDenuncia = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        mensagemDenunciaService.salvarMensagem(idDenuncia, mensagemDenunciaRequestDTO, AutorMensagem.DENUNCIANTE);
    }
}
