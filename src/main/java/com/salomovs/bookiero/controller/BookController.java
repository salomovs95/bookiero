package com.salomovs.bookiero.controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.salomovs.bookiero.exception.AuthorNotFoundException;
import com.salomovs.bookiero.exception.BookNotFoundException;
import com.salomovs.bookiero.model.entity.Author;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.repository.AuthorRepository;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.view.dto.CreateBookDto;
import com.salomovs.bookiero.view.dto.RegisterAuthorDTO;

@Service
public class BookController {
  private AuthorRepository authorRepo;
  private BookRepository bookRepo;

  public BookController(final AuthorRepository authorRepo,
                        final BookRepository bookRepo) {
    this.authorRepo = authorRepo;
    this.bookRepo = bookRepo;
  }

  public Integer create(CreateBookDto dto) {
    Author author = this.findAuthor(dto.authorId());
    Book newBook = this.bookRepo.save(new Book(
      null,
      dto.title(),
      dto.pageCount(),
      dto.esbn(),
      dto.edition(),
      dto.publishYear(),
      dto.category(),
      dto.editor(),
      dto.inStockAmount(),
      dto.bookCover().orElse(null),
      author
    ));

    return newBook.getId();
  }

  public Book findBookById(Integer bookId) {
    return this.bookRepo
               .findById(bookId)
               .orElseThrow(()->new BookNotFoundException(bookId));
  }

  public Slice<Book> paginateBooks(String predicate, Integer page) {
    Pageable pageRequest = PageRequest.of(page, 20);
    return this.bookRepo
               .findByPredicate(predicate, pageRequest);
  }

  public List<Book> listBook() {
    return this.bookRepo
               .findAll();
  }

  public List<Book> listMostBorrowedBooks() {
    return this.bookRepo
               .retrieveMostBorrowsBooks();
  }

  public Integer registerAuthor(RegisterAuthorDTO dto) {
    var author = new Author(
      null,
      dto.fullName(),
      dto.profilePicture()
    );

    return this.authorRepo.save(author).getId();
  }

  public Author findAuthor(Integer authorId) {
    return this.authorRepo
               .findById(authorId)
               .orElseThrow(() -> new AuthorNotFoundException(authorId));
  }

  public List<Author> listMostPopularAuthors() {
    return this.authorRepo
               .retrieveMostPopularAuthors();
  }
}
