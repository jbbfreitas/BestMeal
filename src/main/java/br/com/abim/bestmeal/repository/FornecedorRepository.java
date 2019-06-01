package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Fornecedor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Fornecedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

}
