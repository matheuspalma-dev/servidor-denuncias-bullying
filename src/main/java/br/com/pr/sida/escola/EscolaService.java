package br.com.pr.sida.escola;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteService;
import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaService {
    private final EscolaRepository escolaRepository;
    private final OrgaoCompetenteService orgaoCompetenteService;
    private final PasswordEncoder passwordEncoder;

    public List<EscolaResponseDTO> retornarTodasEscolas() {
        List<Escola> escolas = escolaRepository.findAll();
        List<EscolaResponseDTO> escolaResponseDTO = new ArrayList<>();

        for (Escola escola : escolas) {
            escolaResponseDTO.add(converterEntityEmDTO(escola));
        }
        return escolaResponseDTO;
    }

    private EscolaResponseDTO converterEntityEmDTO(Escola escola){
        EscolaResponseDTO escolaResponseDTO = new EscolaResponseDTO();
        escolaResponseDTO.setId(escola.getId());
        escolaResponseDTO.setNome(escola.getNome());
        escolaResponseDTO.setRedeEnsino(escola.getRedeEnsino());
        return escolaResponseDTO;
    }

    public void adicionarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = criarEscola(escolaRequestResgisterDTO);
        escolaRepository.save(escola);
    }

    private Escola criarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO) {
        Escola escola = new Escola();
        escola.setNome(escolaRequestResgisterDTO.nome());
        escola.setEmail(escolaRequestResgisterDTO.email());
        escola.setAtiva(true);
        escola.setRedeEnsino(escolaRequestResgisterDTO.redeEnsino());
        escola.setSenhaAcesso(criptografarSenha(escolaRequestResgisterDTO.senhaAcesso()));
        escola.setOrgaoCompetente(orgaoCompetenteService.buscarOrgaoCompetentePorId(escolaRequestResgisterDTO.orgaoCompetenteId()));
        return escola;
    }

    private String criptografarSenha(String senha) {
        return passwordEncoder.encode(senha);
    }

    public Escola buscarEscolaPorId(Long escolaId) {
        return escolaRepository.findById(escolaId)
                .orElseThrow(() -> new EntityNotFoundException("Escola não encontrada"));
    }

    public Escola buscarEscolaPorEmail(String email) {
        return escolaRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Escola não encontrada"));
    }

    public Escola buscarEscolaPorEmailSemExcecao(String email) {
        return escolaRepository.findByEmail(email)
                .orElse(null);
    }

    public Escola buscarEscolaPorIdSemExcecao(Long escolaId) {
        return escolaRepository.findById(escolaId)
                .orElse(null);
    }

}
