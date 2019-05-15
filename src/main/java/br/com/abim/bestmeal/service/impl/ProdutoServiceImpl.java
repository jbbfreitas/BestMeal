package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.ProdutoService;
import br.com.abim.bestmeal.domain.Produto;
import br.com.abim.bestmeal.repository.ProdutoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Produto}.
 */
@Service
@Transactional
public class ProdutoServiceImpl implements ProdutoService {

    private final Logger log = LoggerFactory.getLogger(ProdutoServiceImpl.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoServiceImpl(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository;
    }

    /**
     * Save a produto.
     *
     * @param produto the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Produto save(Produto produto) {
        log.debug("Request to save Produto : {}", produto);
        return produtoRepository.save(produto);
    }

    /**
     * Get all the produtos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Produto> findAll(Pageable pageable) {
        log.debug("Request to get all Produtos");
        return produtoRepository.findAll(pageable);
    }


    /**
     * Get one produto by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Produto> findOne(Long id) {
        log.debug("Request to get Produto : {}", id);
        return produtoRepository.findById(id);
    }

    /**
     * Delete the produto by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Produto : {}", id);
        produtoRepository.deleteById(id);
    }
}
