package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.CartaoRecompensa;
import br.com.abim.bestmeal.repository.CartaoRecompensaRepository;
import br.com.abim.bestmeal.service.CartaoRecompensaService;
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

import br.com.abim.bestmeal.domain.enumeration.SituacaoCartao;
/**
 * Integration tests for the {@Link CartaoRecompensaResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class CartaoRecompensaResourceIT {

    private static final String DEFAULT_NOME_CARTAO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_CARTAO = "BBBBBBBBBB";

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_VALIDADE = "04/9064";
    private static final String UPDATED_VALIDADE = "1182";

    private static final Integer DEFAULT_PONTUACAO = 0;
    private static final Integer UPDATED_PONTUACAO = 1;

    private static final SituacaoCartao DEFAULT_SITUACAO = SituacaoCartao.ATIVO;
    private static final SituacaoCartao UPDATED_SITUACAO = SituacaoCartao.INATIVO;

    @Autowired
    private CartaoRecompensaRepository cartaoRecompensaRepository;

    @Autowired
    private CartaoRecompensaService cartaoRecompensaService;

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

    private MockMvc restCartaoRecompensaMockMvc;

    private CartaoRecompensa cartaoRecompensa;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CartaoRecompensaResource cartaoRecompensaResource = new CartaoRecompensaResource(cartaoRecompensaService);
        this.restCartaoRecompensaMockMvc = MockMvcBuilders.standaloneSetup(cartaoRecompensaResource)
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
    public static CartaoRecompensa createEntity(EntityManager em) {
        CartaoRecompensa cartaoRecompensa = new CartaoRecompensa()
            .nomeCartao(DEFAULT_NOME_CARTAO)
            .numero(DEFAULT_NUMERO)
            .validade(DEFAULT_VALIDADE)
            .pontuacao(DEFAULT_PONTUACAO)
            .situacao(DEFAULT_SITUACAO);
        return cartaoRecompensa;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CartaoRecompensa createUpdatedEntity(EntityManager em) {
        CartaoRecompensa cartaoRecompensa = new CartaoRecompensa()
            .nomeCartao(UPDATED_NOME_CARTAO)
            .numero(UPDATED_NUMERO)
            .validade(UPDATED_VALIDADE)
            .pontuacao(UPDATED_PONTUACAO)
            .situacao(UPDATED_SITUACAO);
        return cartaoRecompensa;
    }

    @BeforeEach
    public void initTest() {
        cartaoRecompensa = createEntity(em);
    }

    @Test
    @Transactional
    public void createCartaoRecompensa() throws Exception {
        int databaseSizeBeforeCreate = cartaoRecompensaRepository.findAll().size();

        // Create the CartaoRecompensa
        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isCreated());

        // Validate the CartaoRecompensa in the database
        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeCreate + 1);
        CartaoRecompensa testCartaoRecompensa = cartaoRecompensaList.get(cartaoRecompensaList.size() - 1);
        assertThat(testCartaoRecompensa.getNomeCartao()).isEqualTo(DEFAULT_NOME_CARTAO);
        assertThat(testCartaoRecompensa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testCartaoRecompensa.getValidade()).isEqualTo(DEFAULT_VALIDADE);
        assertThat(testCartaoRecompensa.getPontuacao()).isEqualTo(DEFAULT_PONTUACAO);
        assertThat(testCartaoRecompensa.getSituacao()).isEqualTo(DEFAULT_SITUACAO);
    }

    @Test
    @Transactional
    public void createCartaoRecompensaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = cartaoRecompensaRepository.findAll().size();

        // Create the CartaoRecompensa with an existing ID
        cartaoRecompensa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoRecompensa in the database
        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeCartaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRecompensaRepository.findAll().size();
        // set the field null
        cartaoRecompensa.setNomeCartao(null);

        // Create the CartaoRecompensa, which fails.

        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNumeroIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRecompensaRepository.findAll().size();
        // set the field null
        cartaoRecompensa.setNumero(null);

        // Create the CartaoRecompensa, which fails.

        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValidadeIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRecompensaRepository.findAll().size();
        // set the field null
        cartaoRecompensa.setValidade(null);

        // Create the CartaoRecompensa, which fails.

        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPontuacaoIsRequired() throws Exception {
        int databaseSizeBeforeTest = cartaoRecompensaRepository.findAll().size();
        // set the field null
        cartaoRecompensa.setPontuacao(null);

        // Create the CartaoRecompensa, which fails.

        restCartaoRecompensaMockMvc.perform(post("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCartaoRecompensas() throws Exception {
        // Initialize the database
        cartaoRecompensaRepository.saveAndFlush(cartaoRecompensa);

        // Get all the cartaoRecompensaList
        restCartaoRecompensaMockMvc.perform(get("/api/cartao-recompensas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cartaoRecompensa.getId().intValue())))
            .andExpect(jsonPath("$.[*].nomeCartao").value(hasItem(DEFAULT_NOME_CARTAO.toString())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].validade").value(hasItem(DEFAULT_VALIDADE.toString())))
            .andExpect(jsonPath("$.[*].pontuacao").value(hasItem(DEFAULT_PONTUACAO)))
            .andExpect(jsonPath("$.[*].situacao").value(hasItem(DEFAULT_SITUACAO.toString())));
    }
    
    @Test
    @Transactional
    public void getCartaoRecompensa() throws Exception {
        // Initialize the database
        cartaoRecompensaRepository.saveAndFlush(cartaoRecompensa);

        // Get the cartaoRecompensa
        restCartaoRecompensaMockMvc.perform(get("/api/cartao-recompensas/{id}", cartaoRecompensa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cartaoRecompensa.getId().intValue()))
            .andExpect(jsonPath("$.nomeCartao").value(DEFAULT_NOME_CARTAO.toString()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.validade").value(DEFAULT_VALIDADE.toString()))
            .andExpect(jsonPath("$.pontuacao").value(DEFAULT_PONTUACAO))
            .andExpect(jsonPath("$.situacao").value(DEFAULT_SITUACAO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCartaoRecompensa() throws Exception {
        // Get the cartaoRecompensa
        restCartaoRecompensaMockMvc.perform(get("/api/cartao-recompensas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCartaoRecompensa() throws Exception {
        // Initialize the database
        cartaoRecompensaService.save(cartaoRecompensa);

        int databaseSizeBeforeUpdate = cartaoRecompensaRepository.findAll().size();

        // Update the cartaoRecompensa
        CartaoRecompensa updatedCartaoRecompensa = cartaoRecompensaRepository.findById(cartaoRecompensa.getId()).get();
        // Disconnect from session so that the updates on updatedCartaoRecompensa are not directly saved in db
        em.detach(updatedCartaoRecompensa);
        updatedCartaoRecompensa
            .nomeCartao(UPDATED_NOME_CARTAO)
            .numero(UPDATED_NUMERO)
            .validade(UPDATED_VALIDADE)
            .pontuacao(UPDATED_PONTUACAO)
            .situacao(UPDATED_SITUACAO);

        restCartaoRecompensaMockMvc.perform(put("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCartaoRecompensa)))
            .andExpect(status().isOk());

        // Validate the CartaoRecompensa in the database
        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeUpdate);
        CartaoRecompensa testCartaoRecompensa = cartaoRecompensaList.get(cartaoRecompensaList.size() - 1);
        assertThat(testCartaoRecompensa.getNomeCartao()).isEqualTo(UPDATED_NOME_CARTAO);
        assertThat(testCartaoRecompensa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testCartaoRecompensa.getValidade()).isEqualTo(UPDATED_VALIDADE);
        assertThat(testCartaoRecompensa.getPontuacao()).isEqualTo(UPDATED_PONTUACAO);
        assertThat(testCartaoRecompensa.getSituacao()).isEqualTo(UPDATED_SITUACAO);
    }

    @Test
    @Transactional
    public void updateNonExistingCartaoRecompensa() throws Exception {
        int databaseSizeBeforeUpdate = cartaoRecompensaRepository.findAll().size();

        // Create the CartaoRecompensa

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCartaoRecompensaMockMvc.perform(put("/api/cartao-recompensas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(cartaoRecompensa)))
            .andExpect(status().isBadRequest());

        // Validate the CartaoRecompensa in the database
        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCartaoRecompensa() throws Exception {
        // Initialize the database
        cartaoRecompensaService.save(cartaoRecompensa);

        int databaseSizeBeforeDelete = cartaoRecompensaRepository.findAll().size();

        // Delete the cartaoRecompensa
        restCartaoRecompensaMockMvc.perform(delete("/api/cartao-recompensas/{id}", cartaoRecompensa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CartaoRecompensa> cartaoRecompensaList = cartaoRecompensaRepository.findAll();
        assertThat(cartaoRecompensaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CartaoRecompensa.class);
        CartaoRecompensa cartaoRecompensa1 = new CartaoRecompensa();
        cartaoRecompensa1.setId(1L);
        CartaoRecompensa cartaoRecompensa2 = new CartaoRecompensa();
        cartaoRecompensa2.setId(cartaoRecompensa1.getId());
        assertThat(cartaoRecompensa1).isEqualTo(cartaoRecompensa2);
        cartaoRecompensa2.setId(2L);
        assertThat(cartaoRecompensa1).isNotEqualTo(cartaoRecompensa2);
        cartaoRecompensa1.setId(null);
        assertThat(cartaoRecompensa1).isNotEqualTo(cartaoRecompensa2);
    }
}
