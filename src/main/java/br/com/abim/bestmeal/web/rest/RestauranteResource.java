package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.domain.Restaurante;
import br.com.abim.bestmeal.service.RestauranteService;
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
 * REST controller for managing {@link br.com.abim.bestmeal.domain.Restaurante}.
 */
@RestController
@RequestMapping("/api")
public class RestauranteResource {

    private final Logger log = LoggerFactory.getLogger(RestauranteResource.class);

    private static final String ENTITY_NAME = "restaurante";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RestauranteService restauranteService;

    public RestauranteResource(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    /**
     * {@code POST  /restaurantes} : Create a new restaurante.
     *
     * @param restaurante the restaurante to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new restaurante, or with status {@code 400 (Bad Request)} if the restaurante has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/restaurantes")
    public ResponseEntity<Restaurante> createRestaurante(@Valid @RequestBody Restaurante restaurante) throws URISyntaxException {
        log.debug("REST request to save Restaurante : {}", restaurante);
        if (restaurante.getId() != null) {
            throw new BadRequestAlertException("A new restaurante cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Restaurante result = restauranteService.save(restaurante);
        return ResponseEntity.created(new URI("/api/restaurantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /restaurantes} : Updates an existing restaurante.
     *
     * @param restaurante the restaurante to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated restaurante,
     * or with status {@code 400 (Bad Request)} if the restaurante is not valid,
     * or with status {@code 500 (Internal Server Error)} if the restaurante couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/restaurantes")
    public ResponseEntity<Restaurante> updateRestaurante(@Valid @RequestBody Restaurante restaurante) throws URISyntaxException {
        log.debug("REST request to update Restaurante : {}", restaurante);
        if (restaurante.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Restaurante result = restauranteService.save(restaurante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, restaurante.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /restaurantes} : get all the restaurantes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of restaurantes in body.
     */
    @GetMapping("/restaurantes")
    public ResponseEntity<List<Restaurante>> getAllRestaurantes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of Restaurantes");
        Page<Restaurante> page = restauranteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /restaurantes/:id} : get the "id" restaurante.
     *
     * @param id the id of the restaurante to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the restaurante, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/restaurantes/{id}")
    public ResponseEntity<Restaurante> getRestaurante(@PathVariable Long id) {
        log.debug("REST request to get Restaurante : {}", id);
        Optional<Restaurante> restaurante = restauranteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(restaurante);
    }

    /**
     * {@code DELETE  /restaurantes/:id} : delete the "id" restaurante.
     *
     * @param id the id of the restaurante to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/restaurantes/{id}")
    public ResponseEntity<Void> deleteRestaurante(@PathVariable Long id) {
        log.debug("REST request to delete Restaurante : {}", id);
        restauranteService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
