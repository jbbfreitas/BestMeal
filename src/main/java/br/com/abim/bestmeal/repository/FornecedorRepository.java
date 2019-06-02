package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Fornecedor;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Sfring Data  refository for the Fornecedor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {
    
    @Query("SELECT count(f) FROM Fornecedor f WHERE f.cpf = :cpf and f.id <> :id")
	Long countWithCpf(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT count(f) FROM Fornecedor f WHERE f.cnpj = :cnpj and f.id <> :id")
	Long countWithCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);

    @Query("SELECT count(f) FROM Fornecedor f WHERE f.cpf = :cpf ")
	Long countWithCpfIdNull(@Param("cpf") String cpf);

    @Query("SELECT count(f) FROM Fornecedor f WHERE f.cnpj = :cnpj ")
	Long countWithCnpjIdNull(@Param("cnpj") String cnpj);
}
