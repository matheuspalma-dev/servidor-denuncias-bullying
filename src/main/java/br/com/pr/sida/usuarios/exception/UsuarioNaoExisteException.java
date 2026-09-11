package br.com.pr.sida.usuarios.exception;

import jakarta.persistence.EntityNotFoundException;

public class UsuarioNaoExisteException extends EntityNotFoundException {
    public UsuarioNaoExisteException(String msg) {
        super(msg);
    }
}
