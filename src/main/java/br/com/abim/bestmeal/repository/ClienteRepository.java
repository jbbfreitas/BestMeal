package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Cliente;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cliente entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    @Query("SELECT count(c) FROM Cliente c WHERE c.cpf = :cpf and c.id <> :id")
	Long countWithCpf(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT count(c) FROM Cliente c WHERE c.cnpj = :cnpj and c.id <> :id")
	Long countWithCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);

    @Query("SELECT count(c) FROM Cliente c WHERE c.cpf = :cpf ")
	Long countWithCpfIdNull(@Param("cpf") String cpf);

    @Query("SELECT count(c) FROM Cliente c WHERE c.cnpj = :cnpj ")
	Long countWithCnpjIdNull(@Param("cnpj") String cnpj);

}
