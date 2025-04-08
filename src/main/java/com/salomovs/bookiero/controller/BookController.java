package com.salomovs.bookiero.controller;

import org.springframework.stereotype.Service;

import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.view.dto.CreateBookDto;

@Service
public class BookController {
  private BookRepository bookRepo;

  public BookController(final BookRepository bookRepo) {
    this.bookRepo = bookRepo;
  }

  public Integer create(CreateBookDto dto) {
    Book newBook = this.bookRepo.save(new Book(
      null,
      dto.title(),
      dto.pageCount(),
      dto.esbn(),
      dto.edition(),
      dto.publishYear(),
      dto.category(),
      dto.authorName(),
      dto.editor(),
      dto.inStockAmount()
    ));

    return newBook.getId();
  }

  public Book findBookById(Integer bookId) {
    Book book = this.bookRepo.findById(bookId).orElseThrow(
      ()->new RuntimeException("Book Not Found [" + bookId + "]")
    );
    return book;
  }

  public Iterable<Book> listBook() {
    return this.bookRepo.findAll();
  }
}
