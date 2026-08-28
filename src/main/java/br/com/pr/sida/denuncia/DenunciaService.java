package br.com.pr.sida.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteService;
import br.com.pr.sida.OrgaoCompetente.TipoOrgaoCompetente;
import br.com.pr.sida.acesso.denuncia.AcessoDenunciaService;
import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.enums.Prioridade;
import br.com.pr.sida.escola.EscolaService;
import br.com.pr.sida.status.StatusDenunciaEnum;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.RedeEnsino;
import br.com.pr.sida.mensagem.denuncia.AutorMensagem;
import br.com.pr.sida.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import br.com.pr.sida.responsavel.denuncia.ResponsavelDenunciaService;
import br.com.pr.sida.status.StatusDenunciaService;
import br.com.pr.sida.util.mappers.DenunciaMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final AcessoDenunciaService acessoDenunciaService;
    private final DenunciaRepository denunciaRepository;
    private final MensagemDenunciaService mensagemDenunciaService;
    private final EscolaService escolaService;
    private final StatusDenunciaService statusDenunciaService;
    private final ResponsavelDenunciaService responsavelDenunciaService;
    private final OrgaoCompetenteService orgaoCompetenteService;
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

        statusDenunciaService.adicionarStatusDenuncia(denuncia.getId(), StatusDenunciaEnum.RECEBIDA);

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
        return orgaoCompetenteService.buscarOrgaoCompetentePorTipoDeUnidade(tipoOrgaoCompetente);
    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while(denunciaRepository.existsById(id) || id <= 0);
        return id;
    }

    private Escola localizarEscola(Long idEscola) {
        return escolaService.buscarEscolaPorId(idEscola);
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

    public Denuncia buscarDenunciaPorId(Long idDenuncia) {
        return denunciaRepository.findById(idDenuncia)
                .orElseThrow(() -> new EntityNotFoundException("Denúncia não encontrada"));
    }
}
