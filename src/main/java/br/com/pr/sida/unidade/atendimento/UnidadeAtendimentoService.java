package br.com.pr.sida.unidade.atendimento;

import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRegisterDTO;
import br.com.pr.sida.unidade.atendimento.dto.request.UnidadeAtendimentoRequestDTO;
import br.com.pr.sida.unidade.atendimento.dto.response.UnidadeAtendimentoResponseDTO;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UnidadeAtendimentoService {

    private final UnidadeAtendimentoRepository unidadeAtendimentoRepository;
    private final PasswordEncoder passwordEncoder;
    private final Random random = new Random();

    public UnidadeAtendimentoService(
            UnidadeAtendimentoRepository unidadeAtendimentoRepository,
            PasswordEncoder passwordEncoder
            ) {
        this.unidadeAtendimentoRepository = unidadeAtendimentoRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UnidadeAtendimentoResponseDTO login(UnidadeAtendimentoRequestDTO unidadeAtendimentoRequestDTO){
        UnidadeAtendimento unidadeAtendimento = unidadeAtendimentoRepository.findByNome(unidadeAtendimentoRequestDTO.nome())
                .orElseThrow(() -> new RuntimeException("Unidade de atendimento não encontrada"));

        if (!passwordEncoder.matches(unidadeAtendimentoRequestDTO.senha(), unidadeAtendimento.getSenha())){
            return null;
        } else {
             return converterUnidadeAtendimentoParaDTO(unidadeAtendimento);
        }
    }

    public void cadastro(UnidadeAtendimentoRegisterDTO unidadeAtendimentoRegisterDTO){
        UnidadeAtendimento unidadeAtendimento = criarUnidadeAtendimento(unidadeAtendimentoRegisterDTO);
        unidadeAtendimentoRepository.save(unidadeAtendimento);
    }

    private UnidadeAtendimento criarUnidadeAtendimento(UnidadeAtendimentoRegisterDTO unidadeAtendimentoRegisterDTO){
        UnidadeAtendimento unidadeAtendimento = new UnidadeAtendimento();
        unidadeAtendimento.setId(gerarId());
        unidadeAtendimento.setNome(unidadeAtendimentoRegisterDTO.nome());
        unidadeAtendimento.setSenha(criptografarSenha(unidadeAtendimentoRegisterDTO.senha()));
        unidadeAtendimento.setTipoUnidade(unidadeAtendimentoRegisterDTO.tipoUnidade());
        return unidadeAtendimento;
    }

    private UnidadeAtendimentoResponseDTO converterUnidadeAtendimentoParaDTO(UnidadeAtendimento unidadeAtendimento){
        UnidadeAtendimentoResponseDTO unidadeAtendimentoResponseDTO = new UnidadeAtendimentoResponseDTO();
        unidadeAtendimentoResponseDTO.setId(unidadeAtendimento.getId());
        unidadeAtendimentoResponseDTO.setNome(unidadeAtendimentoResponseDTO.getNome());
        unidadeAtendimentoResponseDTO.setTipoUnidade(unidadeAtendimento.getTipoUnidade());

        return unidadeAtendimentoResponseDTO;
    }

    private Long gerarId(){
        Long id;
        do {
            id = random.nextLong();
        } while(unidadeAtendimentoRepository.existsById(id) || id < 0);

        return id;
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }
}
