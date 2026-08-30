package br.com.pr.sida.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface DenunciaRepository extends JpaRepository<Denuncia, Long> {

    public List<Denuncia> findAllByEscolaId(Long escolaId);
}
