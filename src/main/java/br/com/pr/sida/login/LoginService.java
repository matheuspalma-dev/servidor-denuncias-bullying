package br.com.pr.sida.login;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteRepository;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaRepository;
import br.com.pr.sida.util.enums.TipoLogin;
import br.com.pr.sida.util.loginDTOS.LoginRequestDTO;
import br.com.pr.sida.util.loginDTOS.LoginResponseDTO;
import br.com.pr.sida.util.mappers.LoginMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final EscolaRepository escolaRepository;
    private final OrgaoCompetenteRepository orgaoCompetenteRepository;
    private  final PasswordEncoder passwordEncoder;
    private  final LoginMapper loginMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, TipoLogin tipoLogin) {
        if (tipoLogin == TipoLogin.ESCOLA){
            Escola escola = escolaRepository.findByEmail(loginRequestDTO.getEmail())
                    .orElse(null);

            if (passwordEncoder.matches(loginRequestDTO.getSenha(), escola.getSenhaAcesso())) {
                return loginMapper.devolverLoginResponseDTO(escola.getId(), escola.getNome(), escola.getEmail());
            }
        } else {
            OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(loginRequestDTO.getEmail())
                    .orElse(null);

            if (passwordEncoder.matches(loginRequestDTO.getSenha(), orgaoCompetente.getSenhaAcesso())) {
                return loginMapper.devolverLoginResponseDTO(orgaoCompetente.getId(), orgaoCompetente.getNome(), orgaoCompetente.getEmail());
            }
        }

        throw new BadCredentialsException("Senha incorreta");
    }

    public TipoLogin tipoLogin(String email) {
        Escola escola = escolaRepository.findByEmail(email)
                .orElse(null);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteRepository.findByEmail(email)
                .orElse(null);

        if (escola == null && orgaoCompetente == null) {
            throw new BadCredentialsException("Email não pertence a nenhuma organização");
        }

        if (escola != null){
            return TipoLogin.ESCOLA;
        } else {
            return TipoLogin.ORGAO_COMPETENTE;
        }
    }
}
