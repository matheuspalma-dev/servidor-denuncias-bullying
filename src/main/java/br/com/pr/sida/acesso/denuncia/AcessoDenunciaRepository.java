package br.com.pr.sida.acesso.denuncia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AcessoDenunciaRepository extends JpaRepository<Acesso, Long> {
    Boolean existsByCodigoAcesso(String codigoAcesso);

    Optional<Acesso> findByCodigoAcesso(String codigoAcesso);
}
