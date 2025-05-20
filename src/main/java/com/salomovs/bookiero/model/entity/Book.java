package com.salomovs.bookiero.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="books")
public class Book {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable=false)
  private String title;

  @Column(name="page_count", nullable=false)
  private Integer pageCount;

  @Column(nullable=false)
  private String esbn;

  @Column(nullable=false)
  private String edition;

  @Column(name="publish_year", nullable=false)
  private Integer publishYear;

  @Column(nullable=false)
  private String category;

  @Column(nullable=false)
  private String editor;

  @Column(name="in_stock_amount", nullable=false)
  private Long inStockAmount;

  @Column(name="book_cover", nullable=true)
  private String bookCover;

  @ManyToOne @JoinColumn(name="author_id", referencedColumnName="id")
  private Author author;
}
