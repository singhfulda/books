package com.microservicer.restaurant.web.rest;

import com.microservicer.restaurant.GolaApp;

import com.microservicer.restaurant.domain.Cat;
import com.microservicer.restaurant.repository.CatRepository;
import com.microservicer.restaurant.service.CatService;
import com.microservicer.restaurant.service.dto.CatDTO;
import com.microservicer.restaurant.service.mapper.CatMapper;
import com.microservicer.restaurant.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;


import static com.microservicer.restaurant.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the CatResource REST controller.
 *
 * @see CatResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = GolaApp.class)
public class CatResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_PRICE = new BigDecimal(1);
    private static final BigDecimal UPDATED_PRICE = new BigDecimal(2);

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    @Autowired
    private CatRepository catRepository;

    @Autowired
    private CatMapper catMapper;

    @Autowired
    private CatService catService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCatMockMvc;

    private Cat cat;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CatResource catResource = new CatResource(catService);
        this.restCatMockMvc = MockMvcBuilders.standaloneSetup(catResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Cat createEntity(EntityManager em) {
        Cat cat = new Cat()
            .name(DEFAULT_NAME)
            .price(DEFAULT_PRICE)
            .author(DEFAULT_AUTHOR);
        return cat;
    }

    @Before
    public void initTest() {
        cat = createEntity(em);
    }

    @Test
    @Transactional
    public void createCat() throws Exception {
        int databaseSizeBeforeCreate = catRepository.findAll().size();

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);
        restCatMockMvc.perform(post("/api/cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isCreated());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeCreate + 1);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCat.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testCat.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
    }

    @Test
    @Transactional
    public void createCatWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = catRepository.findAll().size();

        // Create the Cat with an existing ID
        cat.setId(1L);
        CatDTO catDTO = catMapper.toDto(cat);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCatMockMvc.perform(post("/api/cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCats() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get all the catList
        restCatMockMvc.perform(get("/api/cats?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(cat.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR.toString())));
    }
    
    @Test
    @Transactional
    public void getCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        // Get the cat
        restCatMockMvc.perform(get("/api/cats/{id}", cat.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(cat.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCat() throws Exception {
        // Get the cat
        restCatMockMvc.perform(get("/api/cats/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Update the cat
        Cat updatedCat = catRepository.findById(cat.getId()).get();
        // Disconnect from session so that the updates on updatedCat are not directly saved in db
        em.detach(updatedCat);
        updatedCat
            .name(UPDATED_NAME)
            .price(UPDATED_PRICE)
            .author(UPDATED_AUTHOR);
        CatDTO catDTO = catMapper.toDto(updatedCat);

        restCatMockMvc.perform(put("/api/cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isOk());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
        Cat testCat = catList.get(catList.size() - 1);
        assertThat(testCat.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCat.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testCat.getAuthor()).isEqualTo(UPDATED_AUTHOR);
    }

    @Test
    @Transactional
    public void updateNonExistingCat() throws Exception {
        int databaseSizeBeforeUpdate = catRepository.findAll().size();

        // Create the Cat
        CatDTO catDTO = catMapper.toDto(cat);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCatMockMvc.perform(put("/api/cats")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(catDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Cat in the database
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCat() throws Exception {
        // Initialize the database
        catRepository.saveAndFlush(cat);

        int databaseSizeBeforeDelete = catRepository.findAll().size();

        // Get the cat
        restCatMockMvc.perform(delete("/api/cats/{id}", cat.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Cat> catList = catRepository.findAll();
        assertThat(catList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cat.class);
        Cat cat1 = new Cat();
        cat1.setId(1L);
        Cat cat2 = new Cat();
        cat2.setId(cat1.getId());
        assertThat(cat1).isEqualTo(cat2);
        cat2.setId(2L);
        assertThat(cat1).isNotEqualTo(cat2);
        cat1.setId(null);
        assertThat(cat1).isNotEqualTo(cat2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CatDTO.class);
        CatDTO catDTO1 = new CatDTO();
        catDTO1.setId(1L);
        CatDTO catDTO2 = new CatDTO();
        assertThat(catDTO1).isNotEqualTo(catDTO2);
        catDTO2.setId(catDTO1.getId());
        assertThat(catDTO1).isEqualTo(catDTO2);
        catDTO2.setId(2L);
        assertThat(catDTO1).isNotEqualTo(catDTO2);
        catDTO1.setId(null);
        assertThat(catDTO1).isNotEqualTo(catDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(catMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(catMapper.fromId(null)).isNull();
    }
}
