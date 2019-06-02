package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.Restaurante;
import br.com.abim.bestmeal.repository.RestauranteRepository;
import br.com.abim.bestmeal.service.RestauranteService;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static br.com.abim.bestmeal.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import br.com.abim.bestmeal.domain.enumeration.TipoPessoa;
import br.com.abim.bestmeal.domain.enumeration.TipoLogradouro;
/**
 * Integration tests for the {@Link RestauranteResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class RestauranteResourceIT {

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final TipoPessoa DEFAULT_TIPO = TipoPessoa.FISICA;
    private static final TipoPessoa UPDATED_TIPO = TipoPessoa.JURIDICA;

    private static final String DEFAULT_CPF = "AAAAAAAAAA";
    private static final String UPDATED_CPF = "BBBBBBBBBB";

    private static final String DEFAULT_CNPJ = "AAAAAAAAAA";
    private static final String UPDATED_CNPJ = "BBBBBBBBBB";

    private static final String DEFAULT_PRIMEIRO_NOME = "AAAAAAAAAA";
    private static final String UPDATED_PRIMEIRO_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_NOME_MEIO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_MEIO = "BBBBBBBBBB";

    private static final String DEFAULT_SOBRE_NOME = "AAAAAAAAAA";
    private static final String UPDATED_SOBRE_NOME = "BBBBBBBBBB";

    private static final String DEFAULT_SAUDACAO = "AAAAAAAAAA";
    private static final String UPDATED_SAUDACAO = "BBBBBBBBBB";

    private static final String DEFAULT_TITULO = "AAAAAAAAAA";
    private static final String UPDATED_TITULO = "BBBBBBBBBB";

    private static final String DEFAULT_CEP = "44[647-721";
    private static final String UPDATED_CEP = "39V741-749";

    private static final TipoLogradouro DEFAULT_TIPO_LOGRADOURO = TipoLogradouro.RUA;
    private static final TipoLogradouro UPDATED_TIPO_LOGRADOURO = TipoLogradouro.AVENIDA;

    private static final String DEFAULT_NOME_LOGRADOURO = "AAAAAAAAAA";
    private static final String UPDATED_NOME_LOGRADOURO = "BBBBBBBBBB";

    private static final String DEFAULT_COMPLEMENTO = "AAAAAAAAAA";
    private static final String UPDATED_COMPLEMENTO = "BBBBBBBBBB";

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

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

    private MockMvc restRestauranteMockMvc;

    private Restaurante restaurante;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RestauranteResource restauranteResource = new RestauranteResource(restauranteService);
        this.restRestauranteMockMvc = MockMvcBuilders.standaloneSetup(restauranteResource)
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
    public static Restaurante createEntity(EntityManager em) {
        Restaurante restaurante = new Restaurante()
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .tipo(DEFAULT_TIPO)
            .cpf(DEFAULT_CPF)
            .cnpj(DEFAULT_CNPJ)
            .primeiroNome(DEFAULT_PRIMEIRO_NOME)
            .nomeMeio(DEFAULT_NOME_MEIO)
            .sobreNome(DEFAULT_SOBRE_NOME)
            .saudacao(DEFAULT_SAUDACAO)
            .titulo(DEFAULT_TITULO)
            .cep(DEFAULT_CEP)
            .tipoLogradouro(DEFAULT_TIPO_LOGRADOURO)
            .nomeLogradouro(DEFAULT_NOME_LOGRADOURO)
            .complemento(DEFAULT_COMPLEMENTO);
        return restaurante;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Restaurante createUpdatedEntity(EntityManager em) {
        Restaurante restaurante = new Restaurante()
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .nomeMeio(UPDATED_NOME_MEIO)
            .sobreNome(UPDATED_SOBRE_NOME)
            .saudacao(UPDATED_SAUDACAO)
            .titulo(UPDATED_TITULO)
            .cep(UPDATED_CEP)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .nomeLogradouro(UPDATED_NOME_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO);
        return restaurante;
    }

    @BeforeEach
    public void initTest() {
        restaurante = createEntity(em);
    }

    @Test
    @Transactional
    public void createRestaurante() throws Exception {
        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();

        // Create the Restaurante
        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isCreated());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate + 1);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testRestaurante.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testRestaurante.getTipo()).isEqualTo(DEFAULT_TIPO);
        assertThat(testRestaurante.getCpf()).isEqualTo(DEFAULT_CPF);
        assertThat(testRestaurante.getCnpj()).isEqualTo(DEFAULT_CNPJ);
        assertThat(testRestaurante.getPrimeiroNome()).isEqualTo(DEFAULT_PRIMEIRO_NOME);
        assertThat(testRestaurante.getNomeMeio()).isEqualTo(DEFAULT_NOME_MEIO);
        assertThat(testRestaurante.getSobreNome()).isEqualTo(DEFAULT_SOBRE_NOME);
        assertThat(testRestaurante.getSaudacao()).isEqualTo(DEFAULT_SAUDACAO);
        assertThat(testRestaurante.getTitulo()).isEqualTo(DEFAULT_TITULO);
        assertThat(testRestaurante.getCep()).isEqualTo(DEFAULT_CEP);
        assertThat(testRestaurante.getTipoLogradouro()).isEqualTo(DEFAULT_TIPO_LOGRADOURO);
        assertThat(testRestaurante.getNomeLogradouro()).isEqualTo(DEFAULT_NOME_LOGRADOURO);
        assertThat(testRestaurante.getComplemento()).isEqualTo(DEFAULT_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void createRestauranteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = restauranteRepository.findAll().size();

        // Create the Restaurante with an existing ID
        restaurante.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkPrimeiroNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setPrimeiroNome(null);

        // Create the Restaurante, which fails.

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkNomeLogradouroIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setNomeLogradouro(null);

        // Create the Restaurante, which fails.

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkComplementoIsRequired() throws Exception {
        int databaseSizeBeforeTest = restauranteRepository.findAll().size();
        // set the field null
        restaurante.setComplemento(null);

        // Create the Restaurante, which fails.

        restRestauranteMockMvc.perform(post("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllRestaurantes() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        // Get all the restauranteList
        restRestauranteMockMvc.perform(get("/api/restaurantes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(restaurante.getId().intValue())))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())))
            .andExpect(jsonPath("$.[*].cpf").value(hasItem(DEFAULT_CPF.toString())))
            .andExpect(jsonPath("$.[*].cnpj").value(hasItem(DEFAULT_CNPJ.toString())))
            .andExpect(jsonPath("$.[*].primeiroNome").value(hasItem(DEFAULT_PRIMEIRO_NOME.toString())))
            .andExpect(jsonPath("$.[*].nomeMeio").value(hasItem(DEFAULT_NOME_MEIO.toString())))
            .andExpect(jsonPath("$.[*].sobreNome").value(hasItem(DEFAULT_SOBRE_NOME.toString())))
            .andExpect(jsonPath("$.[*].saudacao").value(hasItem(DEFAULT_SAUDACAO.toString())))
            .andExpect(jsonPath("$.[*].titulo").value(hasItem(DEFAULT_TITULO.toString())))
            .andExpect(jsonPath("$.[*].cep").value(hasItem(DEFAULT_CEP.toString())))
            .andExpect(jsonPath("$.[*].tipoLogradouro").value(hasItem(DEFAULT_TIPO_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].nomeLogradouro").value(hasItem(DEFAULT_NOME_LOGRADOURO.toString())))
            .andExpect(jsonPath("$.[*].complemento").value(hasItem(DEFAULT_COMPLEMENTO.toString())));
    }
    
    @Test
    @Transactional
    public void getRestaurante() throws Exception {
        // Initialize the database
        restauranteRepository.saveAndFlush(restaurante);

        // Get the restaurante
        restRestauranteMockMvc.perform(get("/api/restaurantes/{id}", restaurante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(restaurante.getId().intValue()))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()))
            .andExpect(jsonPath("$.cpf").value(DEFAULT_CPF.toString()))
            .andExpect(jsonPath("$.cnpj").value(DEFAULT_CNPJ.toString()))
            .andExpect(jsonPath("$.primeiroNome").value(DEFAULT_PRIMEIRO_NOME.toString()))
            .andExpect(jsonPath("$.nomeMeio").value(DEFAULT_NOME_MEIO.toString()))
            .andExpect(jsonPath("$.sobreNome").value(DEFAULT_SOBRE_NOME.toString()))
            .andExpect(jsonPath("$.saudacao").value(DEFAULT_SAUDACAO.toString()))
            .andExpect(jsonPath("$.titulo").value(DEFAULT_TITULO.toString()))
            .andExpect(jsonPath("$.cep").value(DEFAULT_CEP.toString()))
            .andExpect(jsonPath("$.tipoLogradouro").value(DEFAULT_TIPO_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.nomeLogradouro").value(DEFAULT_NOME_LOGRADOURO.toString()))
            .andExpect(jsonPath("$.complemento").value(DEFAULT_COMPLEMENTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRestaurante() throws Exception {
        // Get the restaurante
        restRestauranteMockMvc.perform(get("/api/restaurantes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRestaurante() throws Exception {
        // Initialize the database
        restauranteService.save(restaurante);

        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Update the restaurante
        Restaurante updatedRestaurante = restauranteRepository.findById(restaurante.getId()).get();
        // Disconnect from session so that the updates on updatedRestaurante are not directly saved in db
        em.detach(updatedRestaurante);
        updatedRestaurante
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .tipo(UPDATED_TIPO)
            .cpf(UPDATED_CPF)
            .cnpj(UPDATED_CNPJ)
            .primeiroNome(UPDATED_PRIMEIRO_NOME)
            .nomeMeio(UPDATED_NOME_MEIO)
            .sobreNome(UPDATED_SOBRE_NOME)
            .saudacao(UPDATED_SAUDACAO)
            .titulo(UPDATED_TITULO)
            .cep(UPDATED_CEP)
            .tipoLogradouro(UPDATED_TIPO_LOGRADOURO)
            .nomeLogradouro(UPDATED_NOME_LOGRADOURO)
            .complemento(UPDATED_COMPLEMENTO);

        restRestauranteMockMvc.perform(put("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRestaurante)))
            .andExpect(status().isOk());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
        Restaurante testRestaurante = restauranteList.get(restauranteList.size() - 1);
        assertThat(testRestaurante.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testRestaurante.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testRestaurante.getTipo()).isEqualTo(UPDATED_TIPO);
        assertThat(testRestaurante.getCpf()).isEqualTo(UPDATED_CPF);
        assertThat(testRestaurante.getCnpj()).isEqualTo(UPDATED_CNPJ);
        assertThat(testRestaurante.getPrimeiroNome()).isEqualTo(UPDATED_PRIMEIRO_NOME);
        assertThat(testRestaurante.getNomeMeio()).isEqualTo(UPDATED_NOME_MEIO);
        assertThat(testRestaurante.getSobreNome()).isEqualTo(UPDATED_SOBRE_NOME);
        assertThat(testRestaurante.getSaudacao()).isEqualTo(UPDATED_SAUDACAO);
        assertThat(testRestaurante.getTitulo()).isEqualTo(UPDATED_TITULO);
        assertThat(testRestaurante.getCep()).isEqualTo(UPDATED_CEP);
        assertThat(testRestaurante.getTipoLogradouro()).isEqualTo(UPDATED_TIPO_LOGRADOURO);
        assertThat(testRestaurante.getNomeLogradouro()).isEqualTo(UPDATED_NOME_LOGRADOURO);
        assertThat(testRestaurante.getComplemento()).isEqualTo(UPDATED_COMPLEMENTO);
    }

    @Test
    @Transactional
    public void updateNonExistingRestaurante() throws Exception {
        int databaseSizeBeforeUpdate = restauranteRepository.findAll().size();

        // Create the Restaurante

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRestauranteMockMvc.perform(put("/api/restaurantes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(restaurante)))
            .andExpect(status().isBadRequest());

        // Validate the Restaurante in the database
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRestaurante() throws Exception {
        // Initialize the database
        restauranteService.save(restaurante);

        int databaseSizeBeforeDelete = restauranteRepository.findAll().size();

        // Delete the restaurante
        restRestauranteMockMvc.perform(delete("/api/restaurantes/{id}", restaurante.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Restaurante> restauranteList = restauranteRepository.findAll();
        assertThat(restauranteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Restaurante.class);
        Restaurante restaurante1 = new Restaurante();
        restaurante1.setId(1L);
        Restaurante restaurante2 = new Restaurante();
        restaurante2.setId(restaurante1.getId());
        assertThat(restaurante1).isEqualTo(restaurante2);
        restaurante2.setId(2L);
        assertThat(restaurante1).isNotEqualTo(restaurante2);
        restaurante1.setId(null);
        assertThat(restaurante1).isNotEqualTo(restaurante2);
    }
}
