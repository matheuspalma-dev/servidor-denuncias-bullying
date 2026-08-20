# Rotas dos Responsáveis pelas Denuncias
* /denuncias/criar
    - POST
    - recebe no body:
        - DenunciaRequestDTO:
            - nomeMunicipio: String
            - nomeEscola: String
            - idadeDenunciante: int
            - generoDenunciante: Enum (MASCULINO, FEMININO, PREFIRO_NAO_INFORMAR)
            - violenciaNaEscola: boolean
            - tipoViolencia: Enum (FISICA, PSICOLOGICA, VERBAL, SEXUAL, CYBERBULLYING, PATRIMONIAL, OUTRA)
            - mensagemDenuncia: String
            - preferenciaEnvio: Enum (POLICIA, CONSELHO_TUTELAR, DIRETOR)
    - retorna:
        - AcessoDenunciaResponseDTO:
            - codigoAcesso: String
            - senhaAcesso: String

* /denuncias/mensagem/criar
    - POST
    - recebe:
        - MensagemDenunciaRequestDTO:
            - idDenuncia: Long
            - autor: Enum (DENUNCIANTE, RESPONSAVEL)
            - mensagem: String
        - retorna:
            - 201