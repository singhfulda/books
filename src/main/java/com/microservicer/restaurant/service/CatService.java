package com.microservicer.restaurant.service;

import com.microservicer.restaurant.domain.Cat;
import com.microservicer.restaurant.repository.CatRepository;
import com.microservicer.restaurant.service.dto.CatDTO;
import com.microservicer.restaurant.service.mapper.CatMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing Cat.
 */
@Service
@Transactional
public class CatService {

    private final Logger log = LoggerFactory.getLogger(CatService.class);

    private final CatRepository catRepository;

    private final CatMapper catMapper;

    public CatService(CatRepository catRepository, CatMapper catMapper) {
        this.catRepository = catRepository;
        this.catMapper = catMapper;
    }

    /**
     * Save a cat.
     *
     * @param catDTO the entity to save
     * @return the persisted entity
     */
    public CatDTO save(CatDTO catDTO) {
        log.debug("Request to save Cat : {}", catDTO);

        Cat cat = catMapper.toEntity(catDTO);
        cat = catRepository.save(cat);
        return catMapper.toDto(cat);
    }

    /**
     * Get all the cats.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<CatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Cats");
        return catRepository.findAll(pageable)
            .map(catMapper::toDto);
    }


    /**
     * Get one cat by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Optional<CatDTO> findOne(Long id) {
        log.debug("Request to get Cat : {}", id);
        return catRepository.findById(id)
            .map(catMapper::toDto);
    }

    /**
     * Delete the cat by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Cat : {}", id);
        catRepository.deleteById(id);
    }
}
