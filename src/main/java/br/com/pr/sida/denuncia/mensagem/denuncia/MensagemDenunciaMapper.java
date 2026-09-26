package br.com.pr.sida.denuncia.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.security.tirar.xss.TirarXssService;
import br.com.pr.sida.shared.Criptografia;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class MensagemDenunciaMapper {

    private final Criptografia criptografia;
    private final TirarXssService tirarXssService;

    public MensagensDenunciaResponseDTO converterEntityEmDTO(MensagemDenuncia mensagemDenuncia){
        MensagensDenunciaResponseDTO mensagensDenunciaResponseDTO = new MensagensDenunciaResponseDTO();
        mensagensDenunciaResponseDTO.setId(mensagemDenuncia.getId());
        mensagensDenunciaResponseDTO.setDataCriacao(mensagemDenuncia.getDataCriacao());
        mensagensDenunciaResponseDTO.setAutorMensagem(mensagemDenuncia.getAutor());
        mensagensDenunciaResponseDTO.setMensagem(descriptografarMensagem(mensagemDenuncia.getMensagem()));
        return mensagensDenunciaResponseDTO;
    }

    public MensagemDenuncia converterDTOEmEntity(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            Denuncia denuncia,
            AutorMensagem autorMensagem
    ){
        MensagemDenuncia mensagemDenuncia = new MensagemDenuncia();
        mensagemDenuncia.setAutor(autorMensagem);
        String mensagemCriptografada = criptografarMensagem(tirarXss(mensagemDenunciaRequestDTO.mensagem()));
        mensagemDenuncia.setMensagem(mensagemCriptografada);
        mensagemDenuncia.setDenuncia(denuncia);
        mensagemDenuncia.setDataCriacao(LocalDate.now());
        return mensagemDenuncia;
    }

    private String criptografarMensagem(String mensagem) {
        return criptografia.criptografarInformacoes(mensagem);
    }

    private String descriptografarMensagem(String mensagemCriptografada){
        return criptografia.descriptografarInformacoes(mensagemCriptografada);
    }

    private String tirarXss(String mensagem){
        return tirarXssService.tirarXss(mensagem);
    }
}
