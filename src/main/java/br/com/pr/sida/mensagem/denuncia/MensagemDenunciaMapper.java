package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MensagemDenunciaMapper {

    private final TextEncryptor textEncryptor;

    public MensagensDenunciaResponseDTO converterEntityEmDTO(MensagemDenuncia mensagemDenuncia){
        MensagensDenunciaResponseDTO mensagensDenunciaResponseDTO = new MensagensDenunciaResponseDTO();
        mensagensDenunciaResponseDTO.setId(mensagemDenuncia.getId());
        mensagensDenunciaResponseDTO.setDataCriacao(mensagemDenuncia.getDataCriacao());
        mensagensDenunciaResponseDTO.setAutorMensagem(mensagemDenuncia.getAutor());
        mensagensDenunciaResponseDTO.setMensagem(descriptografarMensagem(mensagemDenuncia.getMensagem()));
        return mensagensDenunciaResponseDTO;
    }

    private String descriptografarMensagem(String mensagemCriptografada){
        return textEncryptor.decrypt(mensagemCriptografada);
    }
}
