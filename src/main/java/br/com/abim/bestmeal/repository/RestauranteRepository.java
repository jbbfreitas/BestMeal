package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Restaurante;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Restaurante entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long> {
    @Query("SELECT count(r) FROM Restaurante r WHERE r.cpf = :cpf and r.id <> :id")
	Long countWithCpf(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT count(r) FROM Restaurante r WHERE r.cnpj = :cnpj and r.id <> :id")
	Long countWithCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);

    @Query("SELECT count(r) FROM Restaurante r WHERE r.cpf = :cpf ")
	Long countWithCpfIdNull(@Param("cpf") String cpf);

    @Query("SELECT count(r) FROM Restaurante r WHERE r.cnpj = :cnpj ")
	Long countWithCnpjIdNull(@Param("cnpj") String cnpj);
}
