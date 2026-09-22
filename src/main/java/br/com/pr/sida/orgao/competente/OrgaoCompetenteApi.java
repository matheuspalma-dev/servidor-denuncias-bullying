package br.com.pr.sida.orgao.competente;

import br.com.pr.sida.orgao.competente.dto.request.OrgaoCompetenteRegisterDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;

@Tag(name = "Órgão Competente", description = "API para cadastro e gerenciamento de órgãos competentes.")
public interface OrgaoCompetenteApi {

    @Operation(summary = "Registrar órgão competente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Órgão competente registrado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos (campos obrigatórios ausentes, formatos incorretos)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações inválidas",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "O nome é obrigatório",
                                              "instance": "/orgao-competente/registrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: município não foi encontrado)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Município não encontrado",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Municipio não foi cadastrado",
                                              "status": 404,
                                              "detail": "Município não foi cadastrado ou não existe.",
                                              "instance": "/orgao-competente/registrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Erro interno do servidor",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Erro Interno do Servidor",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Erro interno do servidor",
                                              "status": 500,
                                              "detail": "Erro interno do servidor. Tente novamente mais tarde.",
                                              "instance": "/orgao-competente/registrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void registrarOrgaoCompetente(OrgaoCompetenteRegisterDTO orgaoCompetenteRegisterDTO);
}
