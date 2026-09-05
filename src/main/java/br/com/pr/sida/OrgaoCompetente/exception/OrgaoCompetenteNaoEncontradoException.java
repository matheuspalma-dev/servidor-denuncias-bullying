package br.com.pr.sida.OrgaoCompetente.exception;

import jakarta.persistence.EntityNotFoundException;

public class OrgaoCompetenteNaoEncontradoException extends EntityNotFoundException {

    public OrgaoCompetenteNaoEncontradoException(String msg) {
        super(msg);
    }
}
