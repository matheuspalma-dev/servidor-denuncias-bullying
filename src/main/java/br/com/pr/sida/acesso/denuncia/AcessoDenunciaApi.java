package br.com.pr.sida.acesso.denuncia;

import br.com.pr.sida.acesso.denuncia.dto.request.AcessoDenunciaRequestDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResponseDTO;
import br.com.pr.sida.denuncia.dto.response.DenunciaResumoResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Acesso Denúncia", description = "API para acesso e consulta de denúncias.")
public interface AcessoDenunciaApi {

    @Operation(summary = "Acessar denúncia")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Denúncia acessada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos ou informações de acesso incorretas",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações incorretas",
                                    value = """
                                            {
                                              "title": "Informações incorretas",
                                              "status": 400,
                                              "detail": "Informações de acesso incorretas",
                                              "instance": "/acesso/denuncia/acessar",
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
                                              "instance": "/acesso/denuncia/acessar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<DenunciaResponseDTO> acessarDenuncia(
            AcessoDenunciaRequestDTO acessoDenunciaRequestDTO,
            HttpServletResponse response
    );

    @Operation(summary = "Acessar denúncia (Responsável)")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Denúncia recuperada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não tem permissão para acessar a denúncia)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Não tem permissão",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para acessar esta denúncia ou ela não existe.",
                                              "instance": "/acesso/denuncia/acessar/{denunciaId}",
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
                                              "instance": "/acesso/denuncia/acessar/{denunciaId}",
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
                                              "instance": "/acesso/denuncia/acessar/{denunciaId}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<DenunciaResponseDTO> acessarDenunciaResponsavel(Long denunciaId);

    @Operation(summary = "Acessar denúncias da escola")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de denúncias da escola retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não tem permissão para acessar as denúncias desta escola)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Não tem permissão",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para acessar esta denúncia ou ela não existe.",
                                              "instance": "/acesso/denuncia/escola/{escolaId}/denuncias",
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
                                              "instance": "/acesso/denuncia/escola/{escolaId}/denuncias",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<List<DenunciaResumoResponseDTO>> acessarDenunciasEscola(Long escolaId);

    @Operation(summary = "Acessar denúncias do órgão competente")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de denúncias do órgão competente retornada com sucesso"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não tem permissão para acessar as denúncias deste órgão competente)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Não tem permissão",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para acessar esta denúncia ou ela não existe.",
                                              "instance": "/acesso/denuncia/orgaoCompetente/{orgaoCompetenteId}/denuncias",
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
                                              "instance": "/acesso/denuncia/orgaoCompetente/{orgaoCompetenteId}/denuncias",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<List<DenunciaResumoResponseDTO>> acessarDenunciasOrgaoCompetente(Long orgaoCompetenteId);
}
