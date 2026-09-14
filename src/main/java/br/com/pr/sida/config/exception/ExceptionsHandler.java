package br.com.pr.sida.config.exception;

import br.com.pr.sida.municipio.exception.MunicipioJaCadastradoException;
import br.com.pr.sida.municipio.exception.MunicipioNaoFoiCadastradoException;
import br.com.pr.sida.orgao.competente.exception.OrgaoCompetenteNaoEncontradoException;
import br.com.pr.sida.acesso.denuncia.exception.ErroInternoException;
import br.com.pr.sida.denuncia.exception.DenunciaNaoEncontradaException;
import br.com.pr.sida.escola.exception.EscolaNaoEncontradaException;
import br.com.pr.sida.usuarios.exception.InformacoesIncorretasException;
import br.com.pr.sida.security.exception.NaoTemPermissaoException;
import br.com.pr.sida.usuarios.exception.UsuarioNaoExisteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ErroInternoException.class)
    public ProblemDetail handleErroInternoException(ErroInternoException ex){
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocorreu um erro interno inesperado no sistema. Tente novamente mais tarde."
        );

        problemDetail.setTitle("Erro Interno");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(DenunciaNaoEncontradaException.class)
    public ProblemDetail handleDenunciaNaoEncontradaException(DenunciaNaoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Denúncia não encontrada");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(EscolaNaoEncontradaException.class)
    public ProblemDetail handleEscolaNaoEncontradaException(EscolaNaoEncontradaException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Escola não encontrada");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(InformacoesIncorretasException.class)
    public ProblemDetail handleInformacoesIncorretasException(InformacoesIncorretasException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage()
        );

        problemDetail.setTitle("Informações incorretas");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(OrgaoCompetenteNaoEncontradoException.class)
    public ProblemDetail handleOrgaoCompetenteNaoEncontradoException(OrgaoCompetenteNaoEncontradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Orgão competente não encontrado");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(NaoTemPermissaoException.class)
    public ProblemDetail handleNaoTemPermissaoException(NaoTemPermissaoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED,
                ex.getMessage()
        );

        problemDetail.setTitle("Não tem permissão");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(UsuarioNaoExisteException.class)
    public ProblemDetail handleUsuarioNaoExisteException(UsuarioNaoExisteException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Usuario não existe");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(MunicipioJaCadastradoException.class)
    public ProblemDetail handleMunicipioJaCadastradoException(MunicipioJaCadastradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT,
                ex.getMessage()
        );

        problemDetail.setTitle("Municipio já cadastrado");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

    @ExceptionHandler(MunicipioNaoFoiCadastradoException.class)
    public ProblemDetail handleMunicipioNaoFoiCadastradoException(MunicipioNaoFoiCadastradoException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND,
                ex.getMessage()
        );

        problemDetail.setTitle("Municipio não foi cadastrado");
        problemDetail.setProperty("timestamp", Instant.now());

        return problemDetail;
    }

}
