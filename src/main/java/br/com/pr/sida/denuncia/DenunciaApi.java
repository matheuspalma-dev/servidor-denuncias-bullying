package br.com.pr.sida.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.response.AcessoDenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.request.DenunciaRequestDTO;
import br.com.pr.sida.denuncia.mensagem.denuncia.dto.request.MensagemDenunciaRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Denúncia", description = "API para criação de denúncias e envio de mensagens relacionadas a elas.")
public interface DenunciaApi {

    @Operation(summary = "Registrar denúncia anônima")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Denúncia registrada com sucesso"
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
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "A descrição deve conter entre 10 e 2000 caracteres.",
                                              "instance": "/denuncias/criar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: escola não foi encontrada)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Denúncia Não Encontrada",
                                    value = """
                                            {
                                              "title": "Denúncia não encontrada",
                                              "status": 404,
                                              "detail": "O ID fornecido não corresponde a nenhuma denúncia existente.",
                                              "instance": "/denuncias/criar",
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
                                              "instance": "/denuncias/criar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<AcessoDenunciaResponseDTO> criarDenuncia(
            DenunciaRequestDTO denunciaRequestDTO
    );

    @Operation(summary = "Adicionar mensagem à denúncia (Responsável)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Mensagem adicionada com sucesso"
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
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "A mensagem é obrigatória",
                                              "instance": "/denuncias/{denunciaId}/mensagem/criar/responsavel",
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
                                    name = "Denúncia Não Encontrada",
                                    value = """
                                            {
                                              "title": "Denúncia não encontrada",
                                              "status": 404,
                                              "detail": "O ID fornecido não corresponde a nenhuma denúncia existente.",
                                              "instance": "/denuncias/{denunciaId}/mensagem/criar/responsavel",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não tem permissão para acessar a denúncia)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuario não tem permissão",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para acessar esta denúncia ou ela não existe.",
                                              "instance": "/denuncias/{denunciaId}/mensagem/criar/responsavel",
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
                                              "instance": "/denuncias/{denunciaId}/mensagem/criar/responsavel",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void adicionarMensagemDenunciaResponsavel(
            MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO,
            Long denunciaId
    );

    @Operation(summary = "Adicionar mensagem à denúncia (Denunciante)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Mensagem adicionada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos (campos obrigatórios ausentes, formatos incorretos)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações inválidas",
                                    value = """
                                            {                             \s
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "A mensagem é obrigatória",
                                              "instance": "/denuncias/mensagem/criar/denunciante",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                           \s"""
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: denúncia não foi encontrada)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Denúncia Não Encontrada",
                                    value = """
                                            {
                                              "title": "Denúncia não encontrada",
                                              "status": 404,
                                              "detail": "O ID fornecido não corresponde a nenhuma denúncia existente.",
                                              "instance": "/denuncias/mensagem/criar/denunciante",
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
                                              "instance": "/denuncias/mensagem/criar/denunciante",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void adicionarMensagemDenunciaDenunciante(@RequestBody @Valid MensagemDenunciaRequestDTO mensagemDenunciaRequestDTO);
}
