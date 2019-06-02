package br.com.abim.bestmeal.service.impl;

import br.com.abim.bestmeal.service.RestauranteService;
import br.com.abim.bestmeal.domain.Restaurante;
import br.com.abim.bestmeal.repository.RestauranteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Restaurante}.
 */
@Service
@Transactional
public class RestauranteServiceImpl implements RestauranteService {

    private final Logger log = LoggerFactory.getLogger(RestauranteServiceImpl.class);

    private final RestauranteRepository restauranteRepository;

    public RestauranteServiceImpl(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    /**
     * Save a restaurante.
     *
     * @param restaurante the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Restaurante save(Restaurante restaurante) {
        log.debug("Request to save Restaurante : {}", restaurante);
        return restauranteRepository.save(restaurante);
    }

    /**
     * Get all the restaurantes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Restaurante> findAll(Pageable pageable) {
        log.debug("Request to get all Restaurantes");
        return restauranteRepository.findAll(pageable);
    }


    /**
     * Get one restaurante by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Restaurante> findOne(Long id) {
        log.debug("Request to get Restaurante : {}", id);
        return restauranteRepository.findById(id);
    }

    /**
     * Delete the restaurante by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Restaurante : {}", id);
        restauranteRepository.deleteById(id);
    }
    @Override
    public Long countWithCpf(String cpf, Long id) {
        return restauranteRepository.countWithCpf(cpf,id);
    }

    @Override
    public Long countWithCnpj(String cnpj, Long id) {
        return restauranteRepository.countWithCnpj(cnpj,id);
    }

    @Override
    public Long countWithCpf(String cpf) {
        return restauranteRepository.countWithCpfIdNull(cpf);
    }

    @Override
    public Long countWithCnpj(String cnpj) {
        return restauranteRepository.countWithCnpjIdNull(cnpj);
    }
    
}
