package br.com.pr.sida.usuarios.exception;

import org.jspecify.annotations.Nullable;
import org.springframework.security.authentication.BadCredentialsException;

public class InformacoesIncorretasException extends BadCredentialsException {
    public InformacoesIncorretasException(@Nullable String msg) {
        super(msg);
    }
}
