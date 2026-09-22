package br.com.pr.sida.escola;

import br.com.pr.sida.escola.dto.request.EscolaRequestResgisterDTO;
import br.com.pr.sida.escola.dto.response.EscolaResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "Escola", description = "API para gerenciamento de escolas.")
public interface EscolaApi {

    @Operation(summary = "Retornar todas as escolas cadastradas no sistema")
    ResponseEntity<List<EscolaResponseDTO>> retornarTodasEscolas();

    @Operation(summary = "Adicionar uma nova escola ao sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Escola adicionada com sucesso"
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
                                              "detail": "A rede de ensino é obrigatória",
                                              "instance": "/escolas/adicionar",
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
                                    name = "Município Não Encontrado",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Município não encontrado",
                                              "status": 404,
                                              "detail": "O ID fornecido não corresponde a nenhum município existente.",
                                              "instance": "/escolas/adicionar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void adicionarEscola(EscolaRequestResgisterDTO escolaRequestResgisterDTO);
}
