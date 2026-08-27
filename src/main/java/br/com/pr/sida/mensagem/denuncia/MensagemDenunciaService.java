package br.com.pr.sida.mensagem.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaRepository;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaRepository;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.security.service.SecurityService;
import br.com.pr.sida.util.enums.AutorMensagem;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaRepository denunciaRepository;
    private final TextEncryptor criptografarMensagens;
    private final EscolaRepository escolaRepository;
    private final SecurityService securityService;
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;


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

    public void salvarMensagemResponsavel(Long idDenuncia, MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO, String email){
        Denuncia denuncia = denunciaRepository.findById(idDenuncia)
                .orElseThrow(() -> new RuntimeException("Denúncia não encontrada"));

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
