package br.com.abim.bestmeal.repository;

import br.com.abim.bestmeal.domain.Pessoa;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    
    @Query("SELECT count(p) FROM Pessoa p WHERE p.cpf = :cpf and p.id <> :id")
	Long countWithCpf(@Param("cpf") String cpf, @Param("id") Long id);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cnpj = :cnpj and p.id <> :id")
	Long countWithCnpj(@Param("cnpj") String cnpj, @Param("id") Long id);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cpf = :cpf ")
	Long countWithCpfIdNull(@Param("cpf") String cpf);

    @Query("SELECT count(p) FROM Pessoa p WHERE p.cnpj = :cnpj ")
	Long countWithCnpjIdNull(@Param("cnpj") String cnpj);

}
