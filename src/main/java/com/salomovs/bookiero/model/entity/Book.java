package com.salomovs.bookiero.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

  @Column(nullable=false)
  private Integer publishYear;

  @Column(nullable=false)
  private String category;

  @Column(name="author_name", nullable=false)
  private String authorName;

  @Column(nullable=false)
  private String editor;

  @Column(name="in_stock_amount", nullable=false)
  private Integer inStockAmount;
}
