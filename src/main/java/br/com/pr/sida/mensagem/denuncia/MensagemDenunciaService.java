package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaRepository;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaRepository denunciaRepository;
    private final TextEncryptor criptografarMensagens;

    public MensagemDenunciaService(
            MensagemDenunciaRepository mensagemDenunciaRepository,
            DenunciaRepository denunciaRepository,
            @Qualifier("criptografarMensagens") TextEncryptor criptografarMensagens
    )
    {
        this.mensagemDenunciaRepository = mensagemDenunciaRepository;
        this.denunciaRepository = denunciaRepository;
        this.criptografarMensagens = criptografarMensagens;
    }

    public void salvarMensagem(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO
    ){
        Denuncia denuncia = denunciaRepository.findById(mensagemDenunciaRequestDTO.idDenuncia())
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia);
        mensagemDenunciaRepository.save(mensagemDenuncia);
    }

    public String descriptografarMensagem(String mensagemCriptografada) {
        return criptografarMensagens.decrypt(mensagemCriptografada);
    }

    private MensagemDenuncia criarMensagemDenuncia(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            Denuncia denuncia
    ) {
        MensagemDenuncia mensagemDenuncia = new MensagemDenuncia();
        mensagemDenuncia.setAutor(mensagemDenunciaRequestDTO.autor());
        String mensagemCriptografada = criptografarMensagem(mensagemDenunciaRequestDTO.mensagem());
        mensagemDenuncia.setMensagem(mensagemCriptografada);
        mensagemDenuncia.setDenuncia(denuncia);
        return mensagemDenuncia;
    }

    private String criptografarMensagem(String mensagem) {
        return criptografarMensagens.encrypt(mensagem);
    }
}
