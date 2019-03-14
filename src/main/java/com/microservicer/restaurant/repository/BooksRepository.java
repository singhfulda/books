package com.microservicer.restaurant.repository;

import com.microservicer.restaurant.domain.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books, Long>
{
}
