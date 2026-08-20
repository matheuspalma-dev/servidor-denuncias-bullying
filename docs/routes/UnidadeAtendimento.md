# Rotas das Unidades de Atendimento
* /unidade-atendimento/cadastro
    - POST
    - recebe no body:
        - UnidadeAtendimentoRegisterDTO:
            - nome: String
            - tipoUnidade: Enum (DIRETOR_ESCOLA, POLICIA, CONSELHO_TUTELAR, ENCAMINHAMENTO)
            - senha: String
    - retorna:
        - 201

* /unidade-atendimento/login
    - POST
    - recebe no body:
        - UnidadeAtendimentoRequestDTO:
            - nome: String
            - senha: String
        - retorna:
            - UnidadeAtendimentoResponseDTO:
              - id: Long
              - nome: String
              - tipoUnidade: Enum (DIRETOR_ESCOLA, POLICIA, CONSELHO_TUTELAR, ENCAMINHAMENTO)

* /unidade-atendimento/acessar/encaminhamentos/{unidadeId}
- GET
- recebe na url:
    - unidadeId: Long
    - retorna:
      - List<ResponsavelDenunciaEncaminhamentoDTO>:
        - denunciaId: Long
        - dataCriacao: LocalDate (ano-mes-dia)
        - nomeMunicipio: String
        - nomeEscola: String
        - violenciaNaEscola: boolean
        - tipoViolencia: Enum (FISICA, PSICOLOGICA, VERBAL, SEXUAL, CYBERBULLYING, PATRIMONIAL, OUTRA)
        - status: Enum (RECEBIDA, EM_ANALISE, ENCAMINHADA, EM_ACOMPANHAMENTO, RESOLVIDA, ARQUIVADA)
        - preferenciaEnvio: Enum (POLICIA, CONSELHO_TUTELAR, DIRETOR)


* /acessar/encaminhadas/{unidadeId}
- GET
- recebe na url:
    - unidadeId: Long
    - retorna:
        - List<DenunciaResponseDTO>:
            - id: Long
            - dataCriacao: LocalDate (ano-mes-dia)
            - nomeMunicipio: String
            - nomeEscola: String
            - idadeDenunciante: int
            - generoDenunciante = Enum (MASCULINO, FEMININO, PREFIRO_NAO_INFORMAR)
            - violenciaNaEscola: boolean
            - tipoViolencia: Enum (FISICA, PSICOLOGICA, VERBAL, SEXUAL, CYBERBULLYING, PATRIMONIAL, OUTRA)
            - status: Enum (RECEBIDA, EM_ANALISE, ENCAMINHADA, EM_ACOMPANHAMENTO, RESOLVIDA, ARQUIVADA)
            - mensagens: List<MensagensDenunciaResponseDTO>
              -  MensagensDenunciaResponseDTO:
                 - id: Long
                 - autorMensagem: Enum (DENUNCIANTE, RESPONSAVEL)
                 - mensagem: String
                 - dataCriacao: LocalDate (ano-mes-dia)
