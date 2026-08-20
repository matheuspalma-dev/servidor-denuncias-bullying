package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/mensagem/criar")
    @ResponseStatus(HttpStatus.CREATED)
    public void adicionarMensagemDenuncia(@RequestBody MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        mensagemDenunciaService.salvarMensagem(mensagemDenunciaRequestDTO);
    }
}
