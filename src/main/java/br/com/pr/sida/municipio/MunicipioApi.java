package br.com.pr.sida.municipio;

import br.com.pr.sida.municipio.dto.request.MunicipioRequestDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ProblemDetail;

@Tag(name = "Municipio", description = "API para gerenciamento de municípios")
public interface MunicipioApi {
    @Operation(summary = "Cadastrar um novo município")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Município cadastrado com sucesso"
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
                                              "detail": "O código do município deve ser um número positivo.",
                                              "instance": "/municipios/cadastrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Municipio já cadastrado (conflito de dados)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Municipio já cadastrado",
                                    value = """
                                            {
                                              "type": "about:blank",
                                              "title": "Municipio já cadastrado",
                                              "status": 409,
                                              "detail": "Esse município já foi cadastrado.",
                                              "instance": "/municipios/cadastrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void cadastrarMunicipio(MunicipioRequestDTO municipioRequestDTO);
}
