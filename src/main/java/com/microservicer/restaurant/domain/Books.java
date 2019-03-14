package com.microservicer.restaurant.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
public class Books
{

  @Id
  @GeneratedValue
  private  Long id;
  private String name;
  private BigDecimal price;
  private String author;
  private Long pages;

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
  }

  public Long getId()
  {
    return id;
  }

  public void setId( Long id )
  {
    this.id = id;
  }

  public BigDecimal getPrice()
  {
    return price;
  }

  public void setPrice( BigDecimal price )
  {
    this.price = price;
  }

  public String getAuthor()
  {
    return author;
  }

  public void setAuthor( String author )
  {
    this.author = author;
  }

  public Long getPages()
  {
    return pages;
  }

  public void setPages( Long pages )
  {
    this.pages = pages;
  }
}

