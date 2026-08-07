package br.com.pr.sida.autentificacao;

import br.com.pr.sida.usuario.UsuarioService;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class AutentificacaoService {

    private final GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();

    public String gerarCodigoSecreto() {
        GoogleAuthenticatorKey key = googleAuthenticator.createCredentials();
        return key.getKey();
    }

    public String getQrCodeUrl(String nomeUsuario, String codigoSecreto) {
        String emissor = "Sida";
        return String.format("otpauth://totp/%s:%s?secret=%s&issuer=%s", emissor, nomeUsuario, codigoSecreto, emissor);
    }

    public boolean verificarCodigo(String codigoSecreto, String codigo){
        int codigoInt = Integer.parseInt(codigo);
        return googleAuthenticator.authorize(codigoSecreto, codigoInt);
    }
}
