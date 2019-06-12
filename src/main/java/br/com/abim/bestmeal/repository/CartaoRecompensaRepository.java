package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.CartaoRecompensa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CartaoRecompensa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartaoRecompensaRepository extends JpaRepository<CartaoRecompensa, Long> {


    @Query("SELECT c FROM CartaoRecompensa c WHERE c.cliente.id = :id ")
	Page<CartaoRecompensa> findAllClienteCartaoRecompensa(@Param("id") Long id, Pageable pageable);

}
