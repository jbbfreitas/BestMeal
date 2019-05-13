package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.CartaoCredito;
import br.com.abim.bestmeal.repository.CartaoCreditoRepository;
import br.com.abim.bestmeal.service.CartaoCreditoService;
import br.com.abim.bestmeal.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.abim.bestmeal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.abim.bestmeal.domain.enumeration.Bandeira;
/**
 * Integration tests for the {@Link CartaoCreditoResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class CartaoCreditoResourceIT {

    private static final String DEFAULT_NOME_CARTAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CARTAO = "BBBBBBBBBB";

    private static final Bandeira DEFAULT_BANDEIRA = Bandeira.AMERICAN;
    private static final Bandeira UPDATED_BANDEIRA = Bandeira.DINERS;

    private static final String DEFAULT_NUMERO = "298";
    private static final String UPDATED_NUMERO = "150";

    private static final String DEFAULT_CVV = "035";
    private static final String UPDATED_CVV = "1577";

    private static final String DEFAULT_VALIDADE = "068800";
    private static final String UPDATED_VALIDADE = "026125";

    @Autowired
    private CartaoCreditoRepository cartaoCreditoRepository;

    @Autowired
    private CartaoCreditoService cartaoCreditoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCartaoCreditoMockMvc;

    private CartaoCredito cartaoCredito;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartaoCreditoResource cartaoCreditoResource = new CartaoCreditoResource(cartaoCreditoService);
        this.restCartaoCreditoMockMvc = MockMvcBuilders.standaloneSetup(cartaoCreditoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartaoCredito createEntity(EntityManager em) {
        CartaoCredito cartaoCredito = new CartaoCredito()
            .nomeCartao(DEFAULT_NOME_CARTAO)
            .bandeira(DEFAULT_BANDEIRA)
            .numero(DEFAULT_NUMERO)
            .cvv(DEFAULT_CVV)
            .validade(DEFAULT_VALIDADE);
        return cartaoCredito;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartaoCredito createUpdatedEntity(EntityManager em) {
        CartaoCredito cartaoCredito = new CartaoCredito()
            .nomeCartao(UPDATED_NOME_CARTAO)
            .bandeira(UPDATED_BANDEIRA)
            .numero(UPDATED_NUMERO)
            .cvv(UPDATED_CVV)
            .validade(UPDATED_VALIDADE);
        return cartaoCredito;
    }

    @BeforeEach
    public void initTest() {
        cartaoCredito = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartaoCredito() throws Exception {
        int databaseSizeBeforeCreate = cartaoCreditoRepository.findAll().size();

        // Create the CartaoCredito
        restCartaoCreditoMockMvc.perform(post("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isCreated());

        // Validate the CartaoCredito in the database
        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeCreate + 1);
        CartaoCredito testCartaoCredito = cartaoCreditoList.get(cartaoCreditoList.size() - 1);
        assertThat(testCartaoCredito.getNomeCartao()).isEqualTo(DEFAULT_NOME_CARTAO);
        assertThat(testCartaoCredito.getBandeira()).isEqualTo(DEFAULT_BANDEIRA);
        assertThat(testCartaoCredito.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCartaoCredito.getCvv()).isEqualTo(DEFAULT_CVV);
        assertThat(testCartaoCredito.getValidade()).isEqualTo(DEFAULT_VALIDADE);
    }

    @Test
    @Transactional
    public void createCartaoCreditoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartaoCreditoRepository.findAll().size();

        // Create the CartaoCredito with an existing ID
        cartaoCredito.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartaoCreditoMockMvc.perform(post("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoCredito in the database
        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeCartaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoCreditoRepository.findAll().size();
        // set the field null
        cartaoCredito.setNomeCartao(null);

        // Create the CartaoCredito, which fails.

        restCartaoCreditoMockMvc.perform(post("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isBadRequest());

        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCvvIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoCreditoRepository.findAll().size();
        // set the field null
        cartaoCredito.setCvv(null);

        // Create the CartaoCredito, which fails.

        restCartaoCreditoMockMvc.perform(post("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isBadRequest());

        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoCreditoRepository.findAll().size();
        // set the field null
        cartaoCredito.setValidade(null);

        // Create the CartaoCredito, which fails.

        restCartaoCreditoMockMvc.perform(post("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isBadRequest());

        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartaoCreditos() throws Exception {
        // Initialize the database
        cartaoCreditoRepository.saveAndFlush(cartaoCredito);

        // Get all the cartaoCreditoList
        restCartaoCreditoMockMvc.perform(get("/api/cartao-creditos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartaoCredito.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCartao").value(hasItem(DEFAULT_NOME_CARTAO.toString())))
            .andExpect(jsonPath("$.[*].bandeira").value(hasItem(DEFAULT_BANDEIRA.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].cvv").value(hasItem(DEFAULT_CVV.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())));
    }
    
    @Test
    @Transactional
    public void getCartaoCredito() throws Exception {
        // Initialize the database
        cartaoCreditoRepository.saveAndFlush(cartaoCredito);

        // Get the cartaoCredito
        restCartaoCreditoMockMvc.perform(get("/api/cartao-creditos/{id}", cartaoCredito.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartaoCredito.getId().intValue()))
            .andExpect(jsonPath("$.nomeCartao").value(DEFAULT_NOME_CARTAO.toString()))
            .andExpect(jsonPath("$.bandeira").value(DEFAULT_BANDEIRA.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.cvv").value(DEFAULT_CVV.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCartaoCredito() throws Exception {
        // Get the cartaoCredito
        restCartaoCreditoMockMvc.perform(get("/api/cartao-creditos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartaoCredito() throws Exception {
        // Initialize the database
        cartaoCreditoService.save(cartaoCredito);

        int databaseSizeBeforeUpdate = cartaoCreditoRepository.findAll().size();

        // Update the cartaoCredito
        CartaoCredito updatedCartaoCredito = cartaoCreditoRepository.findById(cartaoCredito.getId()).get();
        // Disconnect from session so that the updates on updatedCartaoCredito are not directly saved in db
        em.detach(updatedCartaoCredito);
        updatedCartaoCredito
            .nomeCartao(UPDATED_NOME_CARTAO)
            .bandeira(UPDATED_BANDEIRA)
            .numero(UPDATED_NUMERO)
            .cvv(UPDATED_CVV)
            .validade(UPDATED_VALIDADE);

        restCartaoCreditoMockMvc.perform(put("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartaoCredito)))
            .andExpect(status().isOk());

        // Validate the CartaoCredito in the database
        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeUpdate);
        CartaoCredito testCartaoCredito = cartaoCreditoList.get(cartaoCreditoList.size() - 1);
        assertThat(testCartaoCredito.getNomeCartao()).isEqualTo(UPDATED_NOME_CARTAO);
        assertThat(testCartaoCredito.getBandeira()).isEqualTo(UPDATED_BANDEIRA);
        assertThat(testCartaoCredito.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCartaoCredito.getCvv()).isEqualTo(UPDATED_CVV);
        assertThat(testCartaoCredito.getValidade()).isEqualTo(UPDATED_VALIDADE);
    }

    @Test
    @Transactional
    public void updateNonExistingCartaoCredito() throws Exception {
        int databaseSizeBeforeUpdate = cartaoCreditoRepository.findAll().size();

        // Create the CartaoCredito

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartaoCreditoMockMvc.perform(put("/api/cartao-creditos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoCredito)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoCredito in the database
        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartaoCredito() throws Exception {
        // Initialize the database
        cartaoCreditoService.save(cartaoCredito);

        int databaseSizeBeforeDelete = cartaoCreditoRepository.findAll().size();

        // Delete the cartaoCredito
        restCartaoCreditoMockMvc.perform(delete("/api/cartao-creditos/{id}", cartaoCredito.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CartaoCredito> cartaoCreditoList = cartaoCreditoRepository.findAll();
        assertThat(cartaoCreditoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartaoCredito.class);
        CartaoCredito cartaoCredito1 = new CartaoCredito();
        cartaoCredito1.setId(1L);
        CartaoCredito cartaoCredito2 = new CartaoCredito();
        cartaoCredito2.setId(cartaoCredito1.getId());
        assertThat(cartaoCredito1).isEqualTo(cartaoCredito2);
        cartaoCredito2.setId(2L);
        assertThat(cartaoCredito1).isNotEqualTo(cartaoCredito2);
        cartaoCredito1.setId(null);
        assertThat(cartaoCredito1).isNotEqualTo(cartaoCredito2);
    }
}
