package br.com.pr.sida.denuncia.responsavel.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface ResponsavelDenunciaRepository extends JpaRepository<ResponsavelDenuncia, Long> {

    public List<ResponsavelDenuncia> findByEscolaResponsavelIdAndEscolaVaiTerAcessoIsTrue(Long escolaId);

    public List<ResponsavelDenuncia> findByOrgaoCompetenteResponsavelId(Long orgaoCompetenteId);
}
