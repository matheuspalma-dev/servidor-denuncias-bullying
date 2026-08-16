package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.responsavel.denuncia.dto.request.ResponsavelDenunciaRequestDTO;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaEncaminhamentoDTO;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimento;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimentoService;
import br.com.pr.sida.util.TipoUnidade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ResponsavelDenunciaService {
    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;
    private final UnidadeAtendimentoService unidadeAtendimentoService;
    private final Random random = new Random();

    public ResponsavelDenunciaService(
            ResponsavelDenunciaRepository responsavelDenunciaRepository,
            UnidadeAtendimentoService unidadeAtendimentoService
    )
    {
        this.responsavelDenunciaRepository = responsavelDenunciaRepository;
        this.unidadeAtendimentoService = unidadeAtendimentoService;
    }

    public void salvarResponsavelDenuncia(Denuncia denuncia, TipoUnidade tipoUnidade){
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoService
                                                    .procurarUnidadeAtendimentoPorTipoUnidade(tipoUnidade);

        ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
        responsavelDenuncia.setDenuncia(denuncia);
        responsavelDenuncia.setUnidadeAtendimento(unidadeAtendimento);
        responsavelDenuncia.setId(gerarId());
        responsavelDenunciaRepository.save(responsavelDenuncia);
    }

    public void mudarResponsavelDenuncia(ResponsavelDenunciaRequestDTO responsavelDenunciaRequestDTO){
        ResponsavelDenuncia responsavelDenuncia = responsavelDenunciaRepository.findByDenunciaId(responsavelDenunciaRequestDTO.denunciaId())
                                                    .orElseThrow(() -> new RuntimeException("Responsável pela denúncia não encontrado"));
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoService
                                                    .procurarUnidadeAtendimentoPorTipoUnidade(responsavelDenunciaRequestDTO.tipoUnidade());

        responsavelDenuncia.setUnidadeAtendimento(unidadeAtendimento);
        responsavelDenunciaRepository.save(responsavelDenuncia);
    }

    public void acessarDenunciasResponsavel(Long idUnidadeAtendimento) {
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoService
                                                    .procurarUnidadeAtendimentoPorId(idUnidadeAtendimento);

        if (unidadeAtendimento == null) {
            throw new RuntimeException("Unidade de atendimento não encontrada");
        }

        List<ResponsavelDenuncia> responsavelDenuncias = responsavelDenunciaRepository.findByUnidadeAtendimentoId(unidadeAtendimento.getId());
        List<Denuncia> denuncias = responsavelDenuncias.stream()
                .map(ResponsavelDenuncia::getDenuncia)
                .toList();


    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while (responsavelDenunciaRepository.existsById(id) || id < 0);

        return id;
    }

    private void criarRespostaEncaminhamento(List<Denuncia> denuncias){
        List<ResponsavelDenunciaEncaminhamentoDTO> encaminhamentos;
    }

    private ResponsavelDenunciaEncaminhamentoDTO criarEncaminhamento(Denuncia denuncia){
        ResponsavelDenunciaEncaminhamentoDTO encaminhamentoDTO = new ResponsavelDenunciaEncaminhamentoDTO();
        encaminhamentoDTO.setDenunciaId(denuncia.getId());
        encaminhamentoDTO.setStatus(denuncia.getStatusDenuncia());
        encaminhamentoDTO.setTipoViolencia(denuncia.getTipoViolencia());
        encaminhamentoDTO.setDataCriacao(denuncia.getDataCriacao());
        encaminhamentoDTO.setNomeMunicipio(denuncia.getNomeMunicipio());
        encaminhamentoDTO.setNomeEscola(denuncia.getNomeEscola());
        encaminhamentoDTO.setViolenciaNaEscola(denuncia.isViolenciaNaEscola());
        encaminhamentoDTO.setPreferenciaEnvio(denuncia.getPreferenciaEnvio());
        return encaminhamentoDTO;
    }

    private void criarRespostaDenuncia(){}
}
