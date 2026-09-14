package br.com.pr.sida.municipio.exception;

import jakarta.persistence.EntityExistsException;

public class MunicipioJaCadastradoException extends EntityExistsException {
    public MunicipioJaCadastradoException(String msg) {
        super(msg);
    }
}
