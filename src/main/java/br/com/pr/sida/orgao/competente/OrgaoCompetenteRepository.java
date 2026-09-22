package br.com.pr.sida.orgao.competente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
interface OrgaoCompetenteRepository extends JpaRepository<OrgaoCompetente, Long> {
    public Optional<OrgaoCompetente> findByTipoOrgaoCompetente(TipoOrgaoCompetente tipoOrgaoCompetente);
    public Optional<OrgaoCompetente> findByMunicipioCodigoIbgeAndTipoOrgaoCompetente(Long municipioId, TipoOrgaoCompetente tipoOrgaoCompetente);
}
