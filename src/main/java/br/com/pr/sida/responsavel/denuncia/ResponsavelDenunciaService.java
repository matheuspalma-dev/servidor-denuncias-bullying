package br.com.pr.sida.responsavel.denuncia;

import br.com.pr.sida.denuncia.Denuncia;
import br.com.pr.sida.responsavel.denuncia.dto.request.ResponsavelDenunciaRequestDTO;
import br.com.pr.sida.responsavel.denuncia.dto.response.ResponsavelDenunciaEncaminhamentoDTO;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimento;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimentoRepository;
import br.com.pr.sida.unidade.atendimento.UnidadeAtendimentoService;
import br.com.pr.sida.util.TipoUnidade;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class ResponsavelDenunciaService {
    private final ResponsavelDenunciaRepository responsavelDenunciaRepository;
    private final UnidadeAtendimentoRepository unidadeAtendimentoRepository;
    private final Random random = new Random();

    public ResponsavelDenunciaService(
            ResponsavelDenunciaRepository responsavelDenunciaRepository,
            UnidadeAtendimentoRepository unidadeAtendimentoRepository
    )
    {
        this.responsavelDenunciaRepository = responsavelDenunciaRepository;
        this.unidadeAtendimentoRepository = unidadeAtendimentoRepository;
    }

    public void salvarResponsavelDenuncia(Denuncia denuncia, TipoUnidade tipoUnidade){
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoRepository.findByTipoUnidade(tipoUnidade)
                .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        ResponsavelDenuncia responsavelDenuncia = new ResponsavelDenuncia();
        responsavelDenuncia.setDenuncia(denuncia);
        responsavelDenuncia.setUnidadeAtendimento(unidadeAtendimento);
        responsavelDenuncia.setId(gerarId());
        responsavelDenunciaRepository.save(responsavelDenuncia);
    }

    public void mudarResponsavelDenuncia(ResponsavelDenunciaRequestDTO responsavelDenunciaRequestDTO){
        ResponsavelDenuncia responsavelDenuncia = responsavelDenunciaRepository.findByDenunciaId(responsavelDenunciaRequestDTO.denunciaId())
                                                    .orElseThrow(() -> new RuntimeException("Responsável pela denúncia não encontrado"));
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoRepository.findByTipoUnidade(responsavelDenunciaRequestDTO.tipoUnidade())
                                                    .orElseThrow(() -> new RuntimeException("Unidade não encontrada"));

        responsavelDenuncia.setUnidadeAtendimento(unidadeAtendimento);
        responsavelDenunciaRepository.save(responsavelDenuncia);
    }

    public List<ResponsavelDenunciaEncaminhamentoDTO> acessarDenunciasEncaminhamento(Long idUnidadeAtendimento) {
        List<Denuncia> denuncias = getDenuncias(idUnidadeAtendimento);
        return criarRespostaEncaminhamento(denuncias);
    }

    public void acessarDenunciasResponsavel(Long idUnidadeAtendimento){
        List<Denuncia> denuncias = getDenuncias(idUnidadeAtendimento);
    }

    private List<Denuncia> getDenuncias(Long idUnidadeAtendimento){
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoRepository.findById(idUnidadeAtendimento)
                .orElseThrow(() -> new RuntimeException("Unidade não encontarda"));

        List<ResponsavelDenuncia> responsavelDenuncias = responsavelDenunciaRepository.findByUnidadeAtendimentoId(unidadeAtendimento.getId());
        List<Denuncia> denuncias = responsavelDenuncias.stream()
                .map(ResponsavelDenuncia::getDenuncia)
                .toList();

        return denuncias;
    }

    private Long gerarId() {
        Long id;
        do {
            id = random.nextLong();
        } while (responsavelDenunciaRepository.existsById(id) || id < 0);

        return id;
    }

    private List<ResponsavelDenunciaEncaminhamentoDTO> criarRespostaEncaminhamento(List<Denuncia> denuncias){
        List<ResponsavelDenunciaEncaminhamentoDTO> encaminhamentos = new ArrayList<>();

        for (int i = 0; i < denuncias.size(); i++){
            Denuncia denuncia = denuncias.get(i);
            ResponsavelDenunciaEncaminhamentoDTO responsavelDenunciaEncaminhamentoDTO = criarEncaminhamento(denuncia);
            encaminhamentos.add(responsavelDenunciaEncaminhamentoDTO);
        }

        return encaminhamentos;
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

    private List<ResponsavelDenunciaEncaminhamentoDTO> criarRespostaAcessoDenuncia(){
        return null;
    }
}
