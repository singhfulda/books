package com.microservicer.restaurant.service.mapper;

import com.microservicer.restaurant.domain.Books;
import com.microservicer.restaurant.service.dto.BooksDTO;
import org.mapstruct.Mapper;

@Mapper( componentModel = "spring", uses = {})
public interface BooksMapper extends EntityMapper<BooksDTO, Books>
{
  default Books fromId(Long id) {
    if (id == null) {
      return null;
    }
    Books books = new Books();
    books.setId(id);
    return books;
  }
}
