package br.com.pr.sida.security.exception;

import java.nio.file.AccessDeniedException;

public class NaoTemPermissaoException extends AccessDeniedException {
    public NaoTemPermissaoException(String file) {
        super(file);
    }
}
