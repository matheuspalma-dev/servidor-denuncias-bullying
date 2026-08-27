package br.com.pr.sida.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.acesso.denuncia.AcessoDenunciaService;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaRepository;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenunciaService;
import br.com.pr.sida.status.StatusDenunciaService;
import br.com.pr.sida.util.enums.*;
import br.com.pr.sida.util.mappers.DenunciaMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final AcessoDenunciaService acessoDenunciaService;
    private final DenunciaRepository denunciaRepository;
    private final MensagemDenunciaService mensagemDenunciaService;
    private final EscolaRepository escolaRepository;
    private final StatusDenunciaService statusDenunciaService;
    private final ResponsavelDenunciaService responsavelDenunciaService;
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private final DenunciaMapper denunciaMapper;
    private final Random random = new Random();

    public AcessoDenunciaResponseDTO salvarDenuncia(
            DenunciaRequestDTO denunciaRequestDTO
    )
    {
        Denuncia denuncia = denunciaMapper.converterDTOEmDenuncia(
                denunciaRequestDTO,
                gerarId(),
                localizarEscola(denunciaRequestDTO.idEscola()));
        denunciaRepository.save(denuncia);

        mensagemDenunciaService.salvarMensagem(denuncia.getId(), new MensagemDenunciaRequestDTO(
                denunciaRequestDTO.mensagem()
        ), AutorMensagem.DENUNCIANTE);

        statusDenunciaService.adicionarStatusDenuncia(denuncia.getId(), Status.RECEBIDA);

        Prioridade prioridadeDenuncia = definirPrioridadeDenuncia(denuncia);

        List<OrgaoCompetente> orgaoCompetenteList = new ArrayList<>();

        OrgaoCompetente orgaoCompetente = definirOrgaoCompetente(denuncia.getEscola().getRedeEnsino() == RedeEnsino.MUNICIPAL ? TipoOrgaoCompetente.SME : TipoOrgaoCompetente.NRE);
        if (prioridadeDenuncia == Prioridade.URGENTE){
            orgaoCompetenteList.add(orgaoCompetente);
        } else if (prioridadeDenuncia == Prioridade.ALTA){
            orgaoCompetenteList.add(orgaoCompetente);
            OrgaoCompetente orgaoCompetenteConselhoTutelar = definirOrgaoCompetente(TipoOrgaoCompetente.CONSELHO_TUTELAR);
            orgaoCompetenteList.add(orgaoCompetenteConselhoTutelar);
        }

        responsavelDenunciaService.adicionarResponsavelDenuncia(denuncia, denuncia.getEscola(), orgaoCompetenteList);

        return acessoDenunciaService.salvarAcessoDenuncia(denuncia);
    }

    private OrgaoCompetente definirOrgaoCompetente(TipoOrgaoCompetente tipoOrgaoCompetente){
        return orgaoCompetenteRepository.findByTipoOrgaoCompetente(tipoOrgaoCompetente)
                .orElseThrow(() -> new EntityNotFoundException("orgao competente não existe"));
    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while(denunciaRepository.existsById(id) || id <= 0);
        return id;
    }

    private Escola localizarEscola(Long idEscola) {
        return escolaRepository.findById(idEscola)
                .orElseThrow(() -> new EntityNotFoundException("Escola não encontrada"));
    }

    private Prioridade definirPrioridadeDenuncia(Denuncia denuncia){
        if (denuncia.isRiscoAgressao() || denuncia.isSituacaoGrave()){
            return Prioridade.URGENTE;
        } else if (denuncia.isViolacaoDireitos()){
            return Prioridade.ALTA;
        } else {
            return Prioridade.NORMAL;
        }
    }
}
