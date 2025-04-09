package com.salomovs.bookiero.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.BookBorrow;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.model.repository.BookBorrowRepository;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.model.repository.UserRepository;
import com.salomovs.bookiero.view.dto.CreateBookDto;

@Service
public class BookController {
  private BookRepository bookRepo;
  private BookBorrowRepository borrowRepo;
  private UserRepository userRepo;

  public BookController(final BookRepository bookRepo,
                        final BookBorrowRepository borrowRepo,
                        final UserRepository userRepo) {
    this.bookRepo = bookRepo;
    this.borrowRepo = borrowRepo;
    this.userRepo = userRepo;
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

  public void borrowBook(Integer bookId, Integer userId) {
    // TODO: Check user existence
    User user = this.userRepo.findById(userId).orElseThrow(
      ()->new RuntimeException("User Not Found [" + userId + "]")
    );
    // TODO: Check book existence
    Book book = this.bookRepo.findById(userId).orElseThrow(
      ()->new RuntimeException("Book Not Found [" + bookId + "]")
    );
    // TODO: Check book availability
    List<BookBorrow> borrows = this.borrowRepo.findAllByBookId(bookId);
    if (borrows.stream().filter(borrow->borrow.getReturnedAt() == null).count() >= book.getInStockAmount()) {
      throw new RuntimeException("Book Not Available To Borrow [" + bookId + "]");
    }
    // TODO: Perform book borro7w
    BookBorrow borrow = new BookBorrow(null, LocalDateTime.now(), user, book);
    this.borrowRepo.save(borrow);
  }
}
