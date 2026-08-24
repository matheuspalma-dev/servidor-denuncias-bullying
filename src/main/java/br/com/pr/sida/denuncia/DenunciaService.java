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
import br.com.pr.sida.responsavel.ResponsavelDenunciaService;
import br.com.pr.sida.status.StatusDenunciaService;
import br.com.pr.sida.util.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    private final Random random = new Random();

    public AcessoDenunciaResponseDTO salvarDenuncia(
            DenunciaRequestDTO denunciaRequestDTO
    )
    {
        Denuncia denuncia = criarDenuncia(denunciaRequestDTO);
        denunciaRepository.save(denuncia);

        mensagemDenunciaService.salvarMensagem(new MensagemDenunciaRequestDTO(
                denuncia.getId(),
                AutorMensagem.DENUNCIANTE,
                denunciaRequestDTO.mensagem()
        ));

        statusDenunciaService.atualizarStatusDenuncia(denuncia.getId(), Status.RECEBIDA);

        Prioridade prioridadeDenuncia = definirPrioridadeDenuncia(denuncia);

        if (prioridadeDenuncia == Prioridade.URGENTE){
            if (denuncia.getEscola().getRedeEnsino() == RedeEnsino.MUNICIPAL){
                OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByTipoOrgaoCompetente(TipoOrgaoCompetente.SME)
                        .orElseThrow(() -> new EntityNotFoundException("orgao competente não existe"));
            }
            // Destino = Direção da Escola + Gestor da Rede (SME/NRE)
            // Ação do Sistema = Exibir imediatamente em tela os telefones de emergência (190 PM e 181 Disque Denúncia).
        } else if (prioridadeDenuncia == Prioridade.ALTA){
            // Destino = Direção da Escola + Gestor da Rede (SME/NRE) + CONSELHO TUTELAR
        } else {
            // Direção da Escola (Acesso principal) + Gestor da Rede (Acesso em modo visualização/estatística)
        }

        return acessoDenunciaService.salvarAcessoDenuncia(denuncia);
    }

    private Denuncia criarDenuncia(DenunciaRequestDTO denunciaRequestDTO){
        Denuncia denuncia = new Denuncia();
        denuncia.setId(gerarId());
        denuncia.setDataCriacao(LocalDate.now());
        denuncia.setEscola(localizarEscola(denunciaRequestDTO.idEscola()));
        denuncia.setOndeOcorreu(denunciaRequestDTO.ondeOcorreu());
        denuncia.setTipoViolencia(denunciaRequestDTO.tipoViolencia());
        denuncia.setRiscoAgressao(denunciaRequestDTO.riscoAgressao());
        denuncia.setSituacaoGrave(denunciaRequestDTO.situacaoGrave());
        denuncia.setViolacaoDireitos(denunciaRequestDTO.violacaoDireitos());
        denuncia.setSalaVitimas(denunciaRequestDTO.salaVitimas());
        denuncia.setSalaAgressores(denunciaRequestDTO.salaAgressores());
        return denuncia;
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
