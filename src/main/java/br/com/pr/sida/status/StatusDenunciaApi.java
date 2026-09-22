package br.com.pr.sida.status;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;

@Tag(name = "Status Denúncia", description = "API para atualização do status da denúncia.")
public interface StatusDenunciaApi {

    @Operation(summary = "Atualizar status da denúncia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Status da denúncia atualizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos (identificador ou status da denúncia em formato incorreto)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações inválidas",
                                    value = """
                                            {
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "O valor fornecido para o status da denúncia é inválido.",
                                              "instance": "/status-denuncia/atualizar-status/{denunciaId}/{statusDenunciaEnum}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não tem permissão para acessar ou atualizar a denúncia)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuário não tem permissão",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para acessar esta denúncia ou ela não existe.",
                                              "instance": "/status-denuncia/atualizar-status/{denunciaId}/{statusDenunciaEnum}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: denúncia não foi encontrada)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Denúncia não encontrada",
                                    value = """
                                            {
                                              "title": "Denúncia não encontrada",
                                              "status": 404,
                                              "detail": "Denúncia não encontrada com o ID: 1",
                                              "instance": "/status-denuncia/atualizar-status/{denunciaId}/{statusDenunciaEnum}",
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
                                              "title": "Erro interno do servidor",
                                              "status": 500,
                                              "detail": "Erro interno do servidor. Tente novamente mais tarde.",
                                              "instance": "/status-denuncia/atualizar-status/{denunciaId}/{statusDenunciaEnum}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void atualizarStatusDenuncia(Long denunciaId, StatusDenunciaEnum statusDenunciaEnum);
}
