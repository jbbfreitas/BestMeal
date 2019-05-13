package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.CartaoCredito;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CartaoCredito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {

}
