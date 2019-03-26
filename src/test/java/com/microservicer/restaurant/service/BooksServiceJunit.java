package com.microservicer.restaurant.service;

import com.microservicer.restaurant.GolaApp;
import com.microservicer.restaurant.domain.Books;
import com.microservicer.restaurant.repository.BooksRepository;
import com.microservicer.restaurant.service.dto.BooksDTO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith( SpringRunner.class )
@SpringBootTest( classes = GolaApp.class)
@Transactional
public class BooksServiceJunit
{
  @Autowired
  private BooksRepository booksRepository;

  @Autowired
  public  BooksService booksSevice;

  @Mock
  DateTimeProvider dateTimeProvider;
  private BooksDTO books;

  @Before
  public void init()
  {
    books = new BooksDTO();
    books.setName( "HTML" );
    books.setPages( 123L );

    books.setAuthor( "aaaa" );
    books.setPrice( new BigDecimal(123.678) );

  }

  @Test
  @Transactional
  public void assertSaveUserSavesAUser()
  {

    // Given
    Integer before = booksRepository.findAll().size();

    // When
    books = booksSevice.save( books );

    // Then
    assertNotNull(books.getId());
    Integer after = booksRepository.findAll().size();

    assertEquals( books.getAuthor(), "aaaa" );
    assertThat(after).isEqualTo( before + 1);

  }

  @Test
  @Transactional
  public void updateBooks()
  {
    // Given
    books = booksSevice.save( books );

    // When
    Long idBefore = books.getId();

    books.setAuthor( "bbbb" );
    books.setName( "CSS" );
    books.setPages( 5L );
    books.setPrice( BigDecimal.ONE );

    books = booksSevice.save( books );
    // Then

    Long after = books.getId();

    assertThat( books.getAuthor() ).isEqualTo( "bbbb" );
    assertThat( books.getName() ).isEqualTo( "CSS" );
    assertThat(books.getPrice()).isEqualTo( BigDecimal.ONE );
    assertThat( idBefore ).isEqualTo( after );
  }

  @Test
  @Transactional
  public void deleteBook()
  {
    //Given a book is present in database
    books = booksSevice.save( books);

    //when book is deleted by service
    booksSevice.delete( books.getId() );


    //then book should not be present in database
    Optional<Books> result = booksRepository.findById( books.getId() );
    assertThat( result.isPresent() ).isFalse();

  }

}
