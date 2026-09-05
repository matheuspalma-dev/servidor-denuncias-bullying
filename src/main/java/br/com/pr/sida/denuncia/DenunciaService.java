package br.com.pr.sida.denuncia;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.OrgaoCompetente.TipoOrgaoCompetente;
import br.com.pr.sida.denuncia.como.afetou.ComoAfetouService;
import br.com.pr.sida.denuncia.como.afetou.ComoTeAfetou;
import br.com.pr.sida.denuncia.como.afetou.dto.request.ComoAfetouRequestDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResumoResponseDTO;
import br.com.pr.sida.denuncia.enums.*;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.denuncia.mensagem.denuncia.MensagemDenunciaService;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.response.MensagensDenunciaResponseDTO;
import br.com.pr.sida.denuncia.onde.ocorreu.OndeOcorreuDenunciaService;
import br.com.pr.sida.denuncia.onde.ocorreu.dto.request.OndeOcorreuRequestDTO;
import br.com.pr.sida.denuncia.pratica.acao.PraticaAcaoService;
import br.com.pr.sida.denuncia.pratica.acao.QuemPratica;
import br.com.pr.sida.denuncia.pratica.acao.dto.request.PraticaAcaoRequestDTO;
import br.com.pr.sida.denuncia.responsavel.denuncia.ResponsavelDenuncia;
import br.com.pr.sida.denuncia.responsavel.denuncia.dto.response.ResponsavelDenunciaResponseDTO;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciaService;
import br.com.pr.sida.denuncia.situacao.denuncia.SituacaoDenunciada;
import br.com.pr.sida.denuncia.situacao.denuncia.dto.request.SituacaoDenunciaRequestDTO;
import br.com.pr.sida.status.StatusDenunciaEnum;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.RedeEnsino;
import br.com.pr.sida.denuncia.responsavel.denuncia.ResponsavelDenunciaService;
import br.com.pr.sida.status.StatusDenunciaService;
import br.com.pr.sida.status.dto.response.StatusDenunciaResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class DenunciaService {

    private final DenunciaRepository denunciaRepository;
    private final EscolaServiceReader escolaServiceReader;
    private final StatusDenunciaService statusDenunciaService;
    private final ResponsavelDenunciaService responsavelDenunciaService;
    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private final DenunciaMapper denunciaMapper;
    private final ComoAfetouService comoAfetouService;
    private final PraticaAcaoService praticaAcaoService;
    private final SituacaoDenunciaService situacaoDenunciaService;
    private final MensagemDenunciaService mensagemDenunciaService;
    private final OndeOcorreuDenunciaService ondeOcorreuDenunciaService;
    private final Random random = new Random();

    public Denuncia salvarDenuncia(
            DenunciaRequestDTO denunciaRequestDTO
    )
    {
        Prioridade prioridadeDenuncia = definirPrioridadeDenuncia(denunciaRequestDTO);

        Denuncia denuncia = denunciaMapper.converterDTOEmDenuncia(
                denunciaRequestDTO,
                gerarId(),
                localizarEscola(denunciaRequestDTO.idEscola()),
                prioridadeDenuncia);
        denunciaRepository.save(denuncia);

        adicionarComoAfetou(denuncia, denunciaRequestDTO.comoTeAfetouList());

        statusDenunciaService.adicionarStatusDenuncia(denuncia.getId(), StatusDenunciaEnum.RECEBIDA);

        adicionarQuemPratica(denuncia, denunciaRequestDTO.quemPratica());

        adicionarSituacaoDenunciada(denuncia, denunciaRequestDTO.situacaoDenunciadas());

        adicionarResponsaveisDenuncia(denuncia, prioridadeDenuncia);

        adicionarOndeOcorreuDenuncia(denuncia, denunciaRequestDTO.ondeOcorreuList());

        return denuncia;
    }

    private void adicionarOndeOcorreuDenuncia(Denuncia denuncia, List<OndeOcorreu> ondeOcorreuList){
        for (OndeOcorreu ondeOcorreu : ondeOcorreuList){
            ondeOcorreuDenunciaService.adicionarOndeOcorreuDenuncia(new OndeOcorreuRequestDTO(ondeOcorreu, denuncia));
        }
    }

    private void adicionarResponsaveisDenuncia(Denuncia denuncia, Prioridade prioridadeDenuncia){
        List<OrgaoCompetente> orgaoCompetenteList = new ArrayList<>();

        OrgaoCompetente orgaoCompetente = definirOrgaoCompetente(denuncia.getEscola().getRedeEnsino() == RedeEnsino.MUNICIPAL ? TipoOrgaoCompetente.SME : TipoOrgaoCompetente.NRE);

        boolean houveNegligencia = houveNegligencia(denuncia);

        if (prioridadeDenuncia == Prioridade.URGENTE){
            orgaoCompetenteList.add(orgaoCompetente);
        } else if (prioridadeDenuncia == Prioridade.ALTA){
            orgaoCompetenteList.add(orgaoCompetente);
            OrgaoCompetente orgaoCompetenteConselhoTutelar = definirOrgaoCompetente(TipoOrgaoCompetente.CONSELHO_TUTELAR);
            orgaoCompetenteList.add(orgaoCompetenteConselhoTutelar);
        }

        boolean escolaVaiTerAcesso = escolaVaiTerAcesso(denuncia, houveNegligencia);

        responsavelDenunciaService.adicionarResponsavelDenuncia(denuncia, denuncia.getEscola(), orgaoCompetenteList, escolaVaiTerAcesso);
    }

    private boolean escolaVaiTerAcesso(Denuncia denuncia, boolean houveNegligencia){
        if (houveNegligencia){
            if (denuncia.getRelatadoParaOResponsavel() == RelatadoParaOResponsavel.SIM_EQUIPE_ESCOLA){
                return false;
            }
        }
        return true;
    }

    private boolean houveNegligencia(Denuncia denuncia){
        if (denuncia.getResultadoRelato() == null){
            return false;
        }

        if (denuncia.getResultadoRelato() == ResultadoRelato.NINGUEM_FEZ_NADA || denuncia.getResultadoRelato() == ResultadoRelato.DISSERAM_QUE_NAO_PODIAM_FAZER_NADA){
            return true;
        }

        return false;
    }

    private void adicionarSituacaoDenunciada(Denuncia denuncia, List<SituacaoDenunciada> situacaoDenunciadas){
        for (SituacaoDenunciada situacaoDenunciada : situacaoDenunciadas){
            situacaoDenunciaService.adicionarSituacaoDenuncia(new SituacaoDenunciaRequestDTO(situacaoDenunciada, denuncia));
        }
    }

    private void adicionarComoAfetou(Denuncia denuncia, List<ComoTeAfetou> comoTeAfetouList){
        for (ComoTeAfetou comoTeAfetou : comoTeAfetouList){
            comoAfetouService.salvarComoAfetou(new ComoAfetouRequestDTO(comoTeAfetou, denuncia));
        }
    }

    private void adicionarQuemPratica(Denuncia denuncia, List<QuemPratica> quemPraticaList){
        for (QuemPratica quemPratica : quemPraticaList){
            praticaAcaoService.salvarPraticaAcao(new PraticaAcaoRequestDTO(quemPratica, denuncia));
        }
    }

    private OrgaoCompetente definirOrgaoCompetente(TipoOrgaoCompetente tipoOrgaoCompetente){
        return orgaoCompetenteServiceReader.buscarPorTipoDeUnidade(tipoOrgaoCompetente);
    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while(denunciaRepository.existsById(id) || id <= 0);
        return id;
    }

    private Escola localizarEscola(Long idEscola) {
        return escolaServiceReader.buscarEscolaPorId(idEscola);
    }

    private Prioridade definirPrioridadeDenuncia(DenunciaRequestDTO denuncia){
        if (denuncia.estaEmPerigo()){
            return Prioridade.URGENTE;
        } else if (!denuncia.senteSeguroNaEscola() && (denuncia.frequenciaOcorre() == FrequenciaOcorre.FREQUENTEMENTE || denuncia.frequenciaOcorre() == FrequenciaOcorre.TODOS_OS_DIAS)) {
            return Prioridade.URGENTE;
        } else if (denuncia.frequenciaOcorre() == FrequenciaOcorre.FREQUENTEMENTE) {
            return Prioridade.ALTA;
        } else {
            return Prioridade.NORMAL;
        }
    }

    public DenunciaResponseDTO retornarDenunciaResponseDTO(Denuncia denuncia) {
        List<ComoTeAfetou> comoTeAfetouList = comoAfetouService.retornarComoAfetou(denuncia.getComoTeAfetou());
        List<QuemPratica> quemPraticaList = praticaAcaoService.retornarQuemPratica(denuncia.getPraticantesAcao());
        List<SituacaoDenunciada> situacaoDenunciadaList = situacaoDenunciaService.retornarSituacoesDenunciadas(denuncia.getSituacaoDenuncias());
        List<StatusDenunciaResponseDTO> statusDenunciaResponseDTOList = statusDenunciaService.retornarStatusDenuncia(denuncia.getStatusDenuncia());
        List<MensagensDenunciaResponseDTO> mensagensDenunciaResponseDTOList = mensagemDenunciaService.retornarMensagens(denuncia.getMensagens());
        List<ResponsavelDenunciaResponseDTO> responsavelDenunciaResponseDTOList = responsavelDenunciaService.retornarResponsavelDenuncia(denuncia.getResponsavelDenuncias());
        List<OndeOcorreu> ondeOcorreuList = ondeOcorreuDenunciaService.listarOndeOcorreuDenuncia(denuncia.getOndeOcorreuDenunciaList());
        DenunciaResponseDTO denunciaResponseDTO = denunciaMapper.converterDenunciaEmDTO(
                denuncia,
                comoTeAfetouList,
                quemPraticaList,
                situacaoDenunciadaList,
                statusDenunciaResponseDTOList,
                mensagensDenunciaResponseDTOList,
                responsavelDenunciaResponseDTOList,
                ondeOcorreuList
                );
        return denunciaResponseDTO;
    }

    public List<DenunciaResumoResponseDTO> retornarDenunciasResumo(List<Denuncia> denunciaList) {
        return denunciaMapper.retornarResumoDenunciaDTOList(denunciaList);
    }

    public List<Denuncia> converterResponsavelDenunciaParaDenuncia(List<ResponsavelDenuncia> responsavelList){
        return denunciaMapper.converterResponsavelDenunciaParaDenuncia(responsavelList);
    }
}
