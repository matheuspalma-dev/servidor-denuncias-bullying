package br.com.pr.sida.OrgaoCompetente;

import org.springframework.stereotype.Service;

@Service
public class OrgaoCompetenteService {

    private final OrgaoCompetenteRepository orgaoCompetenteRepository;

    public OrgaoCompetenteService(
            OrgaoCompetenteRepository orgaoCompetenteRepository
    )
    {
        this.orgaoCompetenteRepository = orgaoCompetenteRepository;
    }

}
