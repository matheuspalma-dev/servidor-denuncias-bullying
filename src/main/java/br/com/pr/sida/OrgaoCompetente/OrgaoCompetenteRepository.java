package br.com.pr.sida.OrgaoCompetente;

import br.com.pr.sida.util.enums.TipoOrgaoCompetente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrgaoCompetenteRepository extends JpaRepository<OrgaoCompetente, Long> {
    public Optional<OrgaoCompetente> findByEmail(String email);
    public Optional<OrgaoCompetente> findByTipoOrgaoCompetente(TipoOrgaoCompetente tipoOrgaoCompetente);
}
