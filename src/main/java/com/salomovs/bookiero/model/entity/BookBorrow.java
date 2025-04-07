package com.salomovs.bookiero.model.entity;

import java.time.LocalDateTime;

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
@Entity @Table(name="borrows")
public class BookBorrow {
  @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private Integer id;

  @Column(name="borrowed_at", nullable=false)
  private LocalDateTime borrowedAt;

  @Column(name="returned_at", nullable=true)
  private LocalDateTime returnedAt;

  @ManyToOne @JoinColumn(name="user_id", nullable=false)
  private User whoBorrows;

  @ManyToOne @JoinColumn(name="book_id", nullable=false)
  private Book bookToBorrow;

  public BookBorrow(Integer id, LocalDateTime when, User who, Book what) {
    this.id = id;
    this.borrowedAt = when;
    this.whoBorrows = who;
    this.bookToBorrow = what;
    this.returnedAt = null;
  }
}
