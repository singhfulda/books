package com.microservicer.restaurant.service.mapper;

import com.microservicer.restaurant.domain.*;
import com.microservicer.restaurant.service.dto.CatDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Cat and its DTO CatDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CatMapper extends EntityMapper<CatDTO, Cat> {



    default Cat fromId(Long id) {
        if (id == null) {
            return null;
        }
        Cat cat = new Cat();
        cat.setId(id);
        return cat;
    }
}
