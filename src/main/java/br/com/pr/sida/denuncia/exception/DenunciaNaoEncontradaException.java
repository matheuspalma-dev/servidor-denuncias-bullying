package br.com.pr.sida.denuncia.exception;

import jakarta.persistence.EntityNotFoundException;

public class DenunciaNaoEncontradaException extends EntityNotFoundException {

    public DenunciaNaoEncontradaException(String msg) {
        super(msg);
    }
}
