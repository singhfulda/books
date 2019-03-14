package com.microservicer.restaurant.repository;

import com.microservicer.restaurant.domain.Cat;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Cat entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CatRepository extends JpaRepository<Cat, Long> {

}
