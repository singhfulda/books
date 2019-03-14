package com.microservicer.restaurant.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.microservicer.restaurant.service.BooksService;
import com.microservicer.restaurant.service.dto.BooksDTO;
import com.microservicer.restaurant.web.rest.errors.BadRequestAlertException;
import com.microservicer.restaurant.web.rest.util.HeaderUtil;
import com.microservicer.restaurant.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class BooksResource
{
  private final Logger log= LoggerFactory.getLogger( BooksResource.class );
  private final BooksService booksService;
  private static final String ENTITY_NAME = "books";

  public BooksResource( BooksService booksService )
  {
    this.booksService = booksService;
  }
  @PostMapping("/books")
  @Timed
  public ResponseEntity<BooksDTO> create(@RequestBody BooksDTO booksDTO ) throws URISyntaxException
  {
    if(booksDTO.getId()!=null)
    {
      throw new BadRequestAlertException("A new book cannot already have an ID", ENTITY_NAME, "idexists");
    }
    BooksDTO result = booksService.save( booksDTO );
    return ResponseEntity.created(new URI("/api/books/" + result.getId()))
            .headers( HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
  }

  @DeleteMapping("/books/{id}")
  @Timed
  public ResponseEntity<Void> delete(@PathVariable Long id)
  {
    booksService.delete( id );
    return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
  }
  @PutMapping("/books")
  public ResponseEntity<BooksDTO> update(@RequestBody BooksDTO booksDTO) throws URISyntaxException
  {
    if(booksDTO.getId()==null)
    {
      throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
    }
    BooksDTO result = booksService.save( booksDTO );
    return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, booksDTO.getId().toString()))
            .body(result);
  }

  @GetMapping("/books/{id}")
  @Timed
  public ResponseEntity<BooksDTO> get(@PathVariable Long id)
  {
    Optional<BooksDTO> result = booksService.findone( id );
    return ResponseUtil.wrapOrNotFound(result);

  }

  @GetMapping("/books")
  @Timed
  public ResponseEntity<List<BooksDTO>> getall( Pageable pageable )
  {
    Page<BooksDTO> result = booksService.findAll( pageable );
    HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(result, "/api/books");
    return ResponseEntity.ok().headers(headers).body(result.getContent());

  }

}
