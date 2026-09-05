package br.com.pr.sida.login;

import br.com.pr.sida.OrgaoCompetente.OrgaoCompetente;
import br.com.pr.sida.OrgaoCompetente.OrgaoCompetenteServiceReader;
import br.com.pr.sida.escola.Escola;
import br.com.pr.sida.escola.EscolaServiceReader;
import br.com.pr.sida.login.dto.request.LoginRequestDTO;
import br.com.pr.sida.login.dto.response.LoginResponseDTO;
import br.com.pr.sida.login.exceptions.InformacoesIncorretasException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginService {

    private final EscolaServiceReader escolaServiceReader;
    private final OrgaoCompetenteServiceReader orgaoCompetenteServiceReader;
    private  final PasswordEncoder passwordEncoder;
    private  final LoginMapper loginMapper;

    public LoginResponseDTO login(LoginRequestDTO loginRequestDTO, TipoLogin tipoLogin) {
        if (tipoLogin == TipoLogin.ESCOLA){
            Escola escola = escolaServiceReader.buscarEscolaPorEmailSemExcecao(loginRequestDTO.getEmail());

            if (passwordEncoder.matches(loginRequestDTO.getSenha(), escola.getSenhaAcesso())) {
                return loginMapper.devolverLoginResponseDTO(escola.getId(), escola.getNome(), escola.getEmail());
            }
        } else {
            OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader
                    .buscarPorEmailSemExcessao(loginRequestDTO.getEmail());

            if (passwordEncoder.matches(loginRequestDTO.getSenha(), orgaoCompetente.getSenhaAcesso())) {
                return loginMapper.devolverLoginResponseDTO(orgaoCompetente.getId(), orgaoCompetente.getNome(), orgaoCompetente.getEmail());
            }
        }

        throw new InformacoesIncorretasException("Informações incorretas");
    }

    public TipoLogin tipoLogin(String email) {
        Escola escola = escolaServiceReader.buscarEscolaPorEmailSemExcecao(email);

        OrgaoCompetente orgaoCompetente = orgaoCompetenteServiceReader
                .buscarPorEmailSemExcessao(email);

        if (escola == null && orgaoCompetente == null) {
            throw new InformacoesIncorretasException("Informações incorretas");
        }

        if (escola != null){
            return TipoLogin.ESCOLA;
        } else {
            return TipoLogin.ORGAO_COMPETENTE;
        }
    }
}
