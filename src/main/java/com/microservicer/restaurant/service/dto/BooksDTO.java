package com.microservicer.restaurant.service.dto;

import java.math.BigDecimal;

public class BooksDTO
{
  private  Long id;
  private String name;
  private BigDecimal price;
  private String author;
  private Long pages;

  public Long getId()
  {
    return id;
  }

  public void setId( Long id )
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName( String name )
  {
    this.name = name;
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
