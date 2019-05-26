package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.PessoaService;
import br.com.abim.bestmeal.domain.Pessoa;
import br.com.abim.bestmeal.repository.PessoaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing {@link Pessoa}.
 */
@Service
@Transactional
public class PessoaServiceImpl implements PessoaService {

    private final Logger log = LoggerFactory.getLogger(PessoaServiceImpl.class);

    private final PessoaRepository pessoaRepository;

    public PessoaServiceImpl(PessoaRepository pessoaRepository) {
        this.pessoaRepository = pessoaRepository;
    }

    /**
     * Save a pessoa.
     *
     * @param pessoa the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Pessoa save(Pessoa pessoa) {
        log.debug("Request to save Pessoa : {}", pessoa);
        return pessoaRepository.save(pessoa);
    }

    /**
     * Get all the pessoas.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Pessoa> findAll(Pageable pageable) {
        log.debug("Request to get all Pessoas");
        return pessoaRepository.findAll(pageable);
    }

    /**
     * Get one pessoa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Pessoa> findOne(Long id) {
        log.debug("Request to get Pessoa : {}", id);
        return pessoaRepository.findById(id);
    }

    /**
     * Delete the pessoa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Pessoa : {}", id);
        pessoaRepository.deleteById(id);
    }

    @Override
    public Long countWithCpf(String cpf, Long id) {
        return pessoaRepository.countWithCpf(cpf,id);
    }

    
}
