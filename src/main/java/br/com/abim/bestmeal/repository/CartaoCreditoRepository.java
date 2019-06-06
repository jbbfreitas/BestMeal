package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.CartaoCredito;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CartaoCredito entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoCreditoRepository extends JpaRepository<CartaoCredito, Long> {

    @Query("SELECT c FROM CartaoCredito c WHERE c.cliente.id = :id ")
    Page<CartaoCredito> findAllClienteCartaoCredito(@Param("id") Long id, Pageable pageable );

}
