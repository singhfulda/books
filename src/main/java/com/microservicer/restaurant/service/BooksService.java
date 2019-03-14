package com.microservicer.restaurant.service;

import com.microservicer.restaurant.domain.Books;
import com.microservicer.restaurant.repository.BooksRepository;
import com.microservicer.restaurant.service.dto.BooksDTO;
import com.microservicer.restaurant.service.mapper.BooksMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;



public class BooksService
{
 private final Logger log= LoggerFactory.getLogger(BooksService.class);
 private final BooksMapper booksMapper;
 private final BooksRepository booksRepository;

  public BooksService( BooksMapper booksMapper, BooksRepository booksRepository )
  {
    this.booksMapper = booksMapper;
    this.booksRepository = booksRepository;
  }


  public BooksDTO save( BooksDTO booksDTO){

   Books books= booksMapper.toEntity(booksDTO);
   books= booksRepository.save( books );
   return booksMapper.toDto( books );

  }

  public void delete(Long id)
  {
    booksRepository.deleteById( id );
  }

 @Transactional (readOnly = true)
  public Optional<BooksDTO> findone(Long id)
 {
   return booksRepository.findById( id )
   .map( booksMapper::toDto );

 }

 @Transactional (readOnly = true)
  public Page<BooksDTO> findAll( Pageable pageable )
 {
   return booksRepository.findAll( pageable )
           .map( booksMapper::toDto );
 }

}
