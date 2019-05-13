package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.domain.CartaoCredito;
import br.com.abim.bestmeal.service.CartaoCreditoService;
import br.com.abim.bestmeal.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link br.com.abim.bestmeal.domain.CartaoCredito}.
 */
@RestController
@RequestMapping("/api")
public class CartaoCreditoResource {

    private final Logger log = LoggerFactory.getLogger(CartaoCreditoResource.class);

    private static final String ENTITY_NAME = "cartaoCredito";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CartaoCreditoService cartaoCreditoService;

    public CartaoCreditoResource(CartaoCreditoService cartaoCreditoService) {
        this.cartaoCreditoService = cartaoCreditoService;
    }

    /**
     * {@code POST  /cartao-creditos} : Create a new cartaoCredito.
     *
     * @param cartaoCredito the cartaoCredito to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cartaoCredito, or with status {@code 400 (Bad Request)} if the cartaoCredito has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cartao-creditos")
    public ResponseEntity<CartaoCredito> createCartaoCredito(@Valid @RequestBody CartaoCredito cartaoCredito) throws URISyntaxException {
        log.debug("REST request to save CartaoCredito : {}", cartaoCredito);
        if (cartaoCredito.getId() != null) {
            throw new BadRequestAlertException("A new cartaoCredito cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CartaoCredito result = cartaoCreditoService.save(cartaoCredito);
        return ResponseEntity.created(new URI("/api/cartao-creditos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cartao-creditos} : Updates an existing cartaoCredito.
     *
     * @param cartaoCredito the cartaoCredito to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cartaoCredito,
     * or with status {@code 400 (Bad Request)} if the cartaoCredito is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cartaoCredito couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cartao-creditos")
    public ResponseEntity<CartaoCredito> updateCartaoCredito(@Valid @RequestBody CartaoCredito cartaoCredito) throws URISyntaxException {
        log.debug("REST request to update CartaoCredito : {}", cartaoCredito);
        if (cartaoCredito.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CartaoCredito result = cartaoCreditoService.save(cartaoCredito);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, cartaoCredito.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cartao-creditos} : get all the cartaoCreditos.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cartaoCreditos in body.
     */
    @GetMapping("/cartao-creditos")
    public ResponseEntity<List<CartaoCredito>> getAllCartaoCreditos(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of CartaoCreditos");
        Page<CartaoCredito> page = cartaoCreditoService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /cartao-creditos/:id} : get the "id" cartaoCredito.
     *
     * @param id the id of the cartaoCredito to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cartaoCredito, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cartao-creditos/{id}")
    public ResponseEntity<CartaoCredito> getCartaoCredito(@PathVariable Long id) {
        log.debug("REST request to get CartaoCredito : {}", id);
        Optional<CartaoCredito> cartaoCredito = cartaoCreditoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cartaoCredito);
    }

    /**
     * {@code DELETE  /cartao-creditos/:id} : delete the "id" cartaoCredito.
     *
     * @param id the id of the cartaoCredito to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cartao-creditos/{id}")
    public ResponseEntity<Void> deleteCartaoCredito(@PathVariable Long id) {
        log.debug("REST request to delete CartaoCredito : {}", id);
        cartaoCreditoService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
