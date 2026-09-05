package br.com.pr.sida.login;

import br.com.pr.sida.login.dto.request.LoginRequestDTO;
import br.com.pr.sida.login.dto.response.LoginResponseDTO;
import br.com.pr.sida.security.jwt.TokenService;
import br.com.pr.sida.security.jwt.ROLE;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sida")
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;
    private final TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(
            @RequestBody @Valid LoginRequestDTO loginRequestDTO,
            HttpServletResponse response
    )
    {
        TipoLogin tipoLogin = loginService.tipoLogin(loginRequestDTO.getEmail());
        LoginResponseDTO loginResponseDTO = loginService.login(loginRequestDTO, tipoLogin);
        String token;

        if (tipoLogin == TipoLogin.ESCOLA){
            token = tokenService.gerarTokenUsuario(loginResponseDTO, ROLE.REDE_ENSINO);
        } else { // tipoLogin == TipoLogin.ORGAO_COMPETENTE
            token = tokenService.gerarTokenUsuario(loginResponseDTO, ROLE.ORGAO_COMPETENTE);
        }

        ResponseCookie cookie = ResponseCookie.from("tokenAcesso", token)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600) // 1 hora
                .sameSite("Strict")
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        return ResponseEntity.ok(loginResponseDTO);
    }
}
