package br.com.pr.sida.denuncia.mensagem.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.DenunciaServiceReader;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MensagemDenunciaService {
    private final MensagemDenunciaRepository mensagemDenunciaRepository;
    private final DenunciaServiceReader denunciaServiceReader;
    private final TextEncryptor criptografarMensagens;
    private final MensagemDenunciaMapper mensagemDenunciaMapper;


    @Transactional
    public void salvarMensagem(
            Long idDenuncia,
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            AutorMensagem autorMensagem
    ){
        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(idDenuncia);
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, autorMensagem);
        mensagemDenunciaRepository.save(mensagemDenuncia);
    }

    public void salvarMensagemResponsavel(Long idDenuncia, MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO){
        Denuncia denuncia = denunciaServiceReader.buscarDenunciaPorId(idDenuncia);
        MensagemDenuncia mensagemDenuncia = criarMensagemDenuncia(mensagemDenunciaRequestDTO, denuncia, AutorMensagem.RESPONSAVEL);
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

    public List<MensagensDenunciaResponseDTO> retornarMensagens(List<MensagemDenuncia> mensagemDenunciaList){
        List<MensagensDenunciaResponseDTO> mensagensDenunciaResponseDTOList = new ArrayList<>();
        for (MensagemDenuncia mensagemDenuncia : mensagemDenunciaList){
            mensagensDenunciaResponseDTOList.add(mensagemDenunciaMapper.converterEntityEmDTO(mensagemDenuncia));
        }
        return mensagensDenunciaResponseDTOList;
    }
}
