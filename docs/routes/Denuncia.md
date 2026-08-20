# Rotas das Denuncias
* /responsavel-denuncia/mudar-responsavel
    - POST
    - recebe no body:
        - ResponsavelDenunciaRequestDTO:
            - denunciaId: Long
            - tipoUnidade: Enum (DIRETOR_ESCOLA, POLICIA, CONSELHO_TUTELAR, ENCAMINHAMENTO)
    - retorna:
        - 200