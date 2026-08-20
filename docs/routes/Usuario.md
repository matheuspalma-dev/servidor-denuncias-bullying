# Rotas dos Usuarios
* /usuarios/cadastro
  - POST
  - recebe no body:
    - UsuarioRegisterRequestDTO:
      - nome: String 
      - email: String 
      - senha: String 
      - genero: Enum (MASCULINO, FEMININO, PREFIRO_NAO_INFORMAR)
      - anoNascimento: int
  - retorna:
    - CadastroResponseDTO:
      - qrCodeUrl: String (url que o front trasnforma em qr code)

* /usuarios/login
  - POST
  - recebe no body:
    - UsuarioLoginRequestDTO:
      - email: String
      - senha: String 
    - retorna:
      - 200 (por enquanto)
      
*/usuarios/login/verificacao
  - POST
  - recebe nos parametros da url:
    - params:
      - email: String
      - codigoVerificacao: String
    - retorna:
      - 202 (por enquanto)