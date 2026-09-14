package br.com.pr.sida.municipio.exception;

import jakarta.persistence.EntityNotFoundException;

public class MunicipioNaoFoiCadastradoException extends EntityNotFoundException {
    public MunicipioNaoFoiCadastradoException(String msg) {
        super(msg);
    }
}
