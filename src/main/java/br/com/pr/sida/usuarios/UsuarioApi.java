package br.com.pr.sida.usuarios;

import br.com.pr.sida.usuarios.dto.request.UsuarioLoginRequestDTO;
import br.com.pr.sida.usuarios.dto.request.UsuarioResgisterRequestDTO;
import br.com.pr.sida.usuarios.dto.response.UsuarioLoginResponseDTO;
import br.com.pr.sida.usuarios.lotacao.UsuarioLotacaoEnum;
import br.com.pr.sida.usuarios.lotacao.dto.request.UsuarioLotacaoRequestDTO;
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

@Tag(name = "Usuários", description = "API para gerenciamento de usuários.")
public interface UsuarioApi {

    @Operation(summary = "Cadastrar usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Usuário cadastrado com sucesso"
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
                                              "detail": "O e-mail é obrigatório",
                                              "instance": "/usuarios/cadastrar",
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
                                              "instance": "/usuarios/cadastrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void cadastrarUsuario(UsuarioResgisterRequestDTO usuarioResgisterRequestDTO);

    @Operation(summary = "Adicionar lotação ao usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Lotação adicionada ao usuário com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos ou entidade de lotação não encontrada",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações incorretas",
                                    value = """
                                            {
                                              "title": "Informações incorretas",
                                              "status": 400,
                                              "detail": "Entidade não encontrada",
                                              "instance": "/usuarios/lotacao/cadastrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: usuário não foi encontrado)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuário não encontrado",
                                    value = """
                                            {
                                              "title": "Usuario não existe",
                                              "status": 404,
                                              "detail": "Usuário não encontrado",
                                              "instance": "/usuarios/lotacao/cadastrar",
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
                                              "instance": "/usuarios/lotacao/cadastrar",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    void adicionarLotacao(UsuarioLotacaoRequestDTO usuarioLotacaoRequestDTO);

    @Operation(summary = "Login do usuário")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Login realizado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos ou credenciais incorretas",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações incorretas",
                                    value = """
                                            {
                                              "title": "Informações incorretas",
                                              "status": 400,
                                              "detail": "Informações incorretas",
                                              "instance": "/usuarios/login",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Recurso não encontrado (ex: usuário não cadastrado)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Usuário não encontrado",
                                    value = """
                                            {
                                              "title": "Usuario não existe",
                                              "status": 404,
                                              "detail": "Usuário não encontrado",
                                              "instance": "/usuarios/login",
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
                                              "instance": "/usuarios/login",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<UsuarioLoginResponseDTO> login(
            UsuarioLoginRequestDTO usuarioLoginRequestDTO,
            HttpServletResponse response
    );

    @Operation(summary = "Solicitar acesso ao sistema")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Acesso ao sistema solicitado com sucesso"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Dados de entrada inválidos (identificador ou tipo de entidade em formato incorreto)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Informações inválidas",
                                    value = """
                                            {
                                              "title": "Informações inválidas",
                                              "status": 400,
                                              "detail": "Parâmetros de requisição inválidos.",
                                              "instance": "/usuarios/solicitar-acesso/{entidadeId}/{entidadeTipo}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "Acesso negado (usuário não possui permissão para solicitar acesso ao sistema)",
                    content = @Content(
                            schema = @Schema(implementation = ProblemDetail.class),
                            examples = @ExampleObject(
                                    name = "Acesso negado",
                                    value = """
                                            {
                                              "title": "Não tem permissão",
                                              "status": 403,
                                              "detail": "Você não tem permissão para realizar esta solicitação de acesso.",
                                              "instance": "/usuarios/solicitar-acesso/{entidadeId}/{entidadeTipo}",
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
                                              "instance": "/usuarios/solicitar-acesso/{entidadeId}/{entidadeTipo}",
                                              "timestamp": "2026-09-22T11:00:00Z"
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity solicitarAcessoAoSistema(
            Long entidadeId,
            UsuarioLotacaoEnum entidadeTipo,
            HttpServletResponse response
    );
}
