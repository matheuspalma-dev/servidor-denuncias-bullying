package br.com.pr.sida.util.mappers;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.mensagem.denuncia.MensagemDenuncia;
import br.com.pr.sida.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import br.com.pr.sida.status.StatusDenuncia;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DenunciaMapper {

    private final TextEncryptor textEncryptor;

    public DenunciaResponseDTO converterDenunciaEmDTO(Denuncia denuncia){
        DenunciaResponseDTO denunciaResponseDTO = new DenunciaResponseDTO();
        denunciaResponseDTO.setId(denuncia.getId());
        denunciaResponseDTO.setDataCriacao(denuncia.getDataCriacao());
        denunciaResponseDTO.setNomeEscola(denuncia.getEscola().getNome());
        denunciaResponseDTO.setRedeEnsino(denuncia.getEscola().getRedeEnsino());
        denunciaResponseDTO.setOrgaoCompetenteNome(denuncia.getEscola().getOrgaoCompetente().getNome());
        denunciaResponseDTO.setOndeOcorreu(denuncia.getOndeOcorreu());
        denunciaResponseDTO.setTipoViolencia(denuncia.getTipoViolencia());
        denunciaResponseDTO.setRiscoAgressao(denuncia.isRiscoAgressao());
        denunciaResponseDTO.setSituacaoGrave(denuncia.isSituacaoGrave());
        denunciaResponseDTO.setViolacaoDireitos(denuncia.isViolacaoDireitos());
        List<MensagensDenunciaResponseDTO> mensagensDescriptografadas = new ArrayList<>();

        for (MensagemDenuncia mensagemDenuncia : denuncia.getMensagens()) {
            MensagensDenunciaResponseDTO mensagemDTO = new MensagensDenunciaResponseDTO();
            mensagemDTO.setId(mensagemDenuncia.getId());
            mensagemDTO.setAutorMensagem(mensagemDenuncia.getAutor());
            String mensagemDescriptografada = descriptografarMensagem(mensagemDenuncia.getMensagem());
            mensagemDTO.setMensagem(mensagemDescriptografada);
            mensagemDTO.setDataCriacao(mensagemDenuncia.getDataCriacao());
            mensagensDescriptografadas.add(mensagemDTO);
        }

        denunciaResponseDTO.setMensagens(mensagensDescriptografadas);

        List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList = new ArrayList<>();

        for (StatusDenuncia statusDenuncia : denuncia.getStatusDenuncia()){
            StatusDenunciaResponseDTO statusDenunciaResponseDTO = new StatusDenunciaResponseDTO();
            statusDenunciaResponseDTO.setDataCriacao(statusDenuncia.getDataCriacao());
            statusDenunciaResponseDTO.setStatus(statusDenuncia.getStatus());
            statusDenunciaResponseDTOList.add(statusDenunciaResponseDTO);
        }

        denunciaResponseDTO.setStatusDenuncias(statusDenunciaResponseDTOList);

        List<ResponsavelDenunciaResponseDTO> responsavelDenunciaResponseDTOList = new ArrayList<>();

        for (ResponsavelDenuncia responsavelDenuncia : denuncia.getResponsavelDenuncias()) {
            ResponsavelDenunciaResponseDTO responsavelDenunciaResponseDTO = new ResponsavelDenunciaResponseDTO();
            responsavelDenunciaResponseDTO.setNomeOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getNome());
            responsavelDenunciaResponseDTO.setIdOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getId());
            responsavelDenunciaResponseDTO.setEmailOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getEmail());
            responsavelDenunciaResponseDTO.setNumeroOrgaoCompetenteResponsavel(responsavelDenuncia.getOrgaoCompetenteResponsavel().getNumero());
            responsavelDenunciaResponseDTOList.add(responsavelDenunciaResponseDTO);
        }
        denunciaResponseDTO.setResponsaveisDenuncia(responsavelDenunciaResponseDTOList);
        return denunciaResponseDTO;
    }

    private String descriptografarMensagem(String mensagemCriptografada){
        return textEncryptor.decrypt(mensagemCriptografada);
    }

    public Denuncia converterDTOEmDenuncia(DenunciaRequestDTO denunciaRequestDTO, Long idGerado, Escola escola){
        Denuncia denuncia = new Denuncia();
        denuncia.setId(idGerado);
        denuncia.setDataCriacao(LocalDate.now());
        denuncia.setEscola(escola);
        denuncia.setOndeOcorreu(denunciaRequestDTO.ondeOcorreu());
        denuncia.setTipoViolencia(denunciaRequestDTO.tipoViolencia());
        denuncia.setRiscoAgressao(denunciaRequestDTO.riscoAgressao());
        denuncia.setSituacaoGrave(denunciaRequestDTO.situacaoGrave());
        denuncia.setViolacaoDireitos(denunciaRequestDTO.violacaoDireitos());
        denuncia.setSalaVitimas(denunciaRequestDTO.salaVitimas());
        denuncia.setSalaAgressores(denunciaRequestDTO.salaAgressores());
        return denuncia;
    }


}
