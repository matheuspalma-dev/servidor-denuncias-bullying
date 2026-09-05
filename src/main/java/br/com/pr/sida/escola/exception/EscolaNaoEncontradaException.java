package br.com.pr.sida.escola.exception;

import jakarta.persistence.EntityNotFoundException;

public class EscolaNaoEncontradaException extends EntityNotFoundException {

    public EscolaNaoEncontradaException(String msg) {
        super(msg);
    }
}
