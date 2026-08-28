package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.security.service.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaService denunciaService;
    private final TextEncryptor criptografarMensagens;
    private final SecurityService securityService;


    @Transactional
    public void salvarMensagem(
            Long idDenuncia,
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            AutorMensagem autorMensagem
    ){
        Denuncia denuncia = denunciaService.buscarDenunciaPorId(idDenuncia);
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, autorMensagem);
        mensagemDenunciaRepository.save(mensagemDenuncia);
    }

    public void salvarMensagemResponsavel(Long idDenuncia, MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO, String email){
        Denuncia denuncia = denunciaService.buscarDenunciaPorId(idDenuncia);

        boolean temPermissao = securityService.temPermissaoDeAcessoDenuncia(email, denuncia);

        if (temPermissao) {
            MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, AutorMensagem.RESPONSAVEL);
            mensagemDenunciaRepository.save(mensagemDenuncia);
        } else {
            throw new BadCredentialsException("Usuário não tem permissão para adicionar mensagem a esta denúncia");
        }

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
