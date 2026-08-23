package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.AcessoDenunciaService;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.util.AutorMensagem;
import br.com.pr.sida.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final AcessoDenunciaService acessoDenunciaService;
    private final DenunciaRepository denunciaRepository;
    private final MensagemDenunciaService mensagemDenunciaService;
    private final Random random = new Random();

    public AcessoDenunciaResponseDTO salvarDenuncia(
            DenunciaRequestDTO denunciaRequestDTO
    )
    {
        Denuncia denuncia = criarDenuncia(denunciaRequestDTO);
        denunciaRepository.save(denuncia);

        mensagemDenunciaService.salvarMensagem(new MensagemDenunciaRequestDTO(
                denuncia.getId(),
                AutorMensagem.DENUNCIANTE,
                denunciaRequestDTO.mensagemDenuncia()
        ));

        return acessoDenunciaService.salvarAcessoDenuncia(denuncia);
    }

    private Denuncia criarDenuncia(DenunciaRequestDTO denunciaRequestDTO){
        Denuncia denuncia = new Denuncia();
        denuncia.setDataCriacao(java.time.LocalDate.now());
        denuncia.setNomeMunicipio(denunciaRequestDTO.nomeMunicipio());
        denuncia.setNomeEscola(denunciaRequestDTO.nomeEscola());
        denuncia.setIdadeDenunciante(denunciaRequestDTO.idadeDenunciante());
        denuncia.setGeneroDenunciante(denunciaRequestDTO.generoDenunciante());
        denuncia.setViolenciaNaEscola(denunciaRequestDTO.violenciaNaEscola());
        denuncia.setPreferenciaEnvio(denunciaRequestDTO.preferenciaEnvio());
        if (denunciaRequestDTO.violenciaNaEscola()) {
            denuncia.setTipoViolencia(denunciaRequestDTO.tipoViolencia());
        } else {
            denuncia.setTipoViolencia(null);
        }
        denuncia.setStatusDenuncia(Status.RECEBIDA);
        denuncia.setId(gerarId());

        return denuncia;
    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while(denunciaRepository.existsById(id) || id <= 0);
        return id;
    }
}
