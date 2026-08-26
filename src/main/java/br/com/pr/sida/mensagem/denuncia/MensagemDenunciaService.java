package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaRepository;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.util.enums.AutorMensagem;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaRepository denunciaRepository;
    private final TextEncryptor criptografarMensagens;

    public MensagemDenunciaService(
            MensagemDenunciaRepository mensagemDenunciaRepository,
            DenunciaRepository denunciaRepository,
            TextEncryptor criptografarMensagens
    )
    {
        this.mensagemDenunciaRepository = mensagemDenunciaRepository;
        this.denunciaRepository = denunciaRepository;
        this.criptografarMensagens = criptografarMensagens;
    }

    @Transactional
    public void salvarMensagem(
            Long idDenuncia,
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            AutorMensagem autorMensagem
    ){
        Denuncia denuncia = denunciaRepository.findById(idDenuncia)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, autorMensagem);
        mensagemDenunciaRepository.save(mensagemDenuncia);
    }

    private MensagemDenuncia criarMensagemDenuncia(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            Denuncia denuncia,
            AutorMensagem autorMensagem
    ) {
        MensagemDenuncia mensagemDenuncia = new MensagemDenuncia();
        mensagemDenuncia.setAutor(autorMensagem);
        String mensagemCriptografada = criptografarMensagem(mensagemDenunciaRequestDTO.mensagem());
        mensagemDenuncia.setMensagem(mensagemCriptografada);
        mensagemDenuncia.setDenuncia(denuncia);
        mensagemDenuncia.setDataCriacao(LocalDate.now());
        return mensagemDenuncia;
    }

    private String criptografarMensagem(String mensagem) {
        return criptografarMensagens.encrypt(mensagem);
    }
}
