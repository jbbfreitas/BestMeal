package br.com.abim.bestmeal.service;

import br.com.abim.bestmeal.domain.Pessoa;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link Pessoa}.
 */
public interface PessoaService {

    /**
     * Save a pessoa.
     *
     * @param pessoa the entity to save.
     * @return the persisted entity.
     */
    Pessoa save(Pessoa pessoa);

    /**
     * Get all the pessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Pessoa> findAll(Pageable pageable);


    /**
     * Get the "id" pessoa.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Pessoa> findOne(Long id);

    /**
     * Delete the "id" pessoa.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

	Long countWithCpf(String cpf, Long id);

	Long countWithCnpj(String cnpj, Long id);
	Long countWithCpf(String cpf);

	Long countWithCnpj(String cnpj);
}
