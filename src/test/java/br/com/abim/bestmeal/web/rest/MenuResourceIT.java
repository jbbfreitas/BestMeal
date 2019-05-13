package br.com.abim.bestmeal.web.rest;

import br.com.abim.bestmeal.BestMealApp;
import br.com.abim.bestmeal.domain.Menu;
import br.com.abim.bestmeal.repository.MenuRepository;
import br.com.abim.bestmeal.service.MenuService;
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

import br.com.abim.bestmeal.domain.enumeration.GrupoMenu;
/**
 * Integration tests for the {@Link MenuResource} REST controller.
 */
@SpringBootTest(classes = BestMealApp.class)
public class MenuResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final GrupoMenu DEFAULT_GRUPO = GrupoMenu.ENTRADA;
    private static final GrupoMenu UPDATED_GRUPO = GrupoMenu.PRATOFRIO;

    private static final Double DEFAULT_VALOR_NORMAL = 0D;
    private static final Double UPDATED_VALOR_NORMAL = 1D;

    private static final Double DEFAULT_TEMPO_PREPARO = 0D;
    private static final Double UPDATED_TEMPO_PREPARO = 1D;

    private static final Boolean DEFAULT_IS_DISPONIVEL = false;
    private static final Boolean UPDATED_IS_DISPONIVEL = true;

    private static final Double DEFAULT_VALOR_REDUZIDO = 0D;
    private static final Double UPDATED_VALOR_REDUZIDO = 1D;

    @Autowired
    private MenuRepository menuRepository;

    @Autowired
    private MenuService menuService;

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

    private MockMvc restMenuMockMvc;

    private Menu menu;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MenuResource menuResource = new MenuResource(menuService);
        this.restMenuMockMvc = MockMvcBuilders.standaloneSetup(menuResource)
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
    public static Menu createEntity(EntityManager em) {
        Menu menu = new Menu()
            .nome(DEFAULT_NOME)
            .grupo(DEFAULT_GRUPO)
            .valorNormal(DEFAULT_VALOR_NORMAL)
            .tempoPreparo(DEFAULT_TEMPO_PREPARO)
            .isDisponivel(DEFAULT_IS_DISPONIVEL)
            .valorReduzido(DEFAULT_VALOR_REDUZIDO);
        return menu;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Menu createUpdatedEntity(EntityManager em) {
        Menu menu = new Menu()
            .nome(UPDATED_NOME)
            .grupo(UPDATED_GRUPO)
            .valorNormal(UPDATED_VALOR_NORMAL)
            .tempoPreparo(UPDATED_TEMPO_PREPARO)
            .isDisponivel(UPDATED_IS_DISPONIVEL)
            .valorReduzido(UPDATED_VALOR_REDUZIDO);
        return menu;
    }

    @BeforeEach
    public void initTest() {
        menu = createEntity(em);
    }

    @Test
    @Transactional
    public void createMenu() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isCreated());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate + 1);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getNome()).isEqualTo(DEFAULT_NOME);
        assertThat(testMenu.getGrupo()).isEqualTo(DEFAULT_GRUPO);
        assertThat(testMenu.getValorNormal()).isEqualTo(DEFAULT_VALOR_NORMAL);
        assertThat(testMenu.getTempoPreparo()).isEqualTo(DEFAULT_TEMPO_PREPARO);
        assertThat(testMenu.isIsDisponivel()).isEqualTo(DEFAULT_IS_DISPONIVEL);
        assertThat(testMenu.getValorReduzido()).isEqualTo(DEFAULT_VALOR_REDUZIDO);
    }

    @Test
    @Transactional
    public void createMenuWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = menuRepository.findAll().size();

        // Create the Menu with an existing ID
        menu.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNomeIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setNome(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorNormalIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setValorNormal(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTempoPreparoIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setTempoPreparo(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkIsDisponivelIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setIsDisponivel(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkValorReduzidoIsRequired() throws Exception {
        int databaseSizeBeforeTest = menuRepository.findAll().size();
        // set the field null
        menu.setValorReduzido(null);

        // Create the Menu, which fails.

        restMenuMockMvc.perform(post("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMenus() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get all the menuList
        restMenuMockMvc.perform(get("/api/menus?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(menu.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME.toString())))
            .andExpect(jsonPath("$.[*].grupo").value(hasItem(DEFAULT_GRUPO.toString())))
            .andExpect(jsonPath("$.[*].valorNormal").value(hasItem(DEFAULT_VALOR_NORMAL.doubleValue())))
            .andExpect(jsonPath("$.[*].tempoPreparo").value(hasItem(DEFAULT_TEMPO_PREPARO.doubleValue())))
            .andExpect(jsonPath("$.[*].isDisponivel").value(hasItem(DEFAULT_IS_DISPONIVEL.booleanValue())))
            .andExpect(jsonPath("$.[*].valorReduzido").value(hasItem(DEFAULT_VALOR_REDUZIDO.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getMenu() throws Exception {
        // Initialize the database
        menuRepository.saveAndFlush(menu);

        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", menu.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(menu.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME.toString()))
            .andExpect(jsonPath("$.grupo").value(DEFAULT_GRUPO.toString()))
            .andExpect(jsonPath("$.valorNormal").value(DEFAULT_VALOR_NORMAL.doubleValue()))
            .andExpect(jsonPath("$.tempoPreparo").value(DEFAULT_TEMPO_PREPARO.doubleValue()))
            .andExpect(jsonPath("$.isDisponivel").value(DEFAULT_IS_DISPONIVEL.booleanValue()))
            .andExpect(jsonPath("$.valorReduzido").value(DEFAULT_VALOR_REDUZIDO.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMenu() throws Exception {
        // Get the menu
        restMenuMockMvc.perform(get("/api/menus/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMenu() throws Exception {
        // Initialize the database
        menuService.save(menu);

        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Update the menu
        Menu updatedMenu = menuRepository.findById(menu.getId()).get();
        // Disconnect from session so that the updates on updatedMenu are not directly saved in db
        em.detach(updatedMenu);
        updatedMenu
            .nome(UPDATED_NOME)
            .grupo(UPDATED_GRUPO)
            .valorNormal(UPDATED_VALOR_NORMAL)
            .tempoPreparo(UPDATED_TEMPO_PREPARO)
            .isDisponivel(UPDATED_IS_DISPONIVEL)
            .valorReduzido(UPDATED_VALOR_REDUZIDO);

        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMenu)))
            .andExpect(status().isOk());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
        Menu testMenu = menuList.get(menuList.size() - 1);
        assertThat(testMenu.getNome()).isEqualTo(UPDATED_NOME);
        assertThat(testMenu.getGrupo()).isEqualTo(UPDATED_GRUPO);
        assertThat(testMenu.getValorNormal()).isEqualTo(UPDATED_VALOR_NORMAL);
        assertThat(testMenu.getTempoPreparo()).isEqualTo(UPDATED_TEMPO_PREPARO);
        assertThat(testMenu.isIsDisponivel()).isEqualTo(UPDATED_IS_DISPONIVEL);
        assertThat(testMenu.getValorReduzido()).isEqualTo(UPDATED_VALOR_REDUZIDO);
    }

    @Test
    @Transactional
    public void updateNonExistingMenu() throws Exception {
        int databaseSizeBeforeUpdate = menuRepository.findAll().size();

        // Create the Menu

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMenuMockMvc.perform(put("/api/menus")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(menu)))
            .andExpect(status().isBadRequest());

        // Validate the Menu in the database
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMenu() throws Exception {
        // Initialize the database
        menuService.save(menu);

        int databaseSizeBeforeDelete = menuRepository.findAll().size();

        // Delete the menu
        restMenuMockMvc.perform(delete("/api/menus/{id}", menu.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Menu> menuList = menuRepository.findAll();
        assertThat(menuList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Menu.class);
        Menu menu1 = new Menu();
        menu1.setId(1L);
        Menu menu2 = new Menu();
        menu2.setId(menu1.getId());
        assertThat(menu1).isEqualTo(menu2);
        menu2.setId(2L);
        assertThat(menu1).isNotEqualTo(menu2);
        menu1.setId(null);
        assertThat(menu1).isNotEqualTo(menu2);
    }
}
