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

  public Integer borrowBook(Integer bookId, Integer userId) {
    // TODO: Check user existence
    User user = this.userRepo.findById(userId).orElseThrow(
      ()->new RuntimeException("User Not Found [" + userId + "]")
    );

    // TODO: Check book existence
    Book book = this.findBookById(bookId);
    System.out.println("BOOK EXISTS");

    // TODO: Check book availability
    List<BookBorrow> borrows = this.borrowRepo.findByBookId(book.getId());
    System.out.println("HOW MUCH BORROWS: " + borrows.size());
    if (borrows.size() >= book.getInStockAmount()) {
      throw new RuntimeException("BOOK NOT AVAILABLE TO BORROW");
    }

    // TODO: Check if ueer has already borrowed this book
    borrows.stream().forEach(b->{
      if (b.getWhoBorrows().getId().equals(userId)) {
        throw new RuntimeException("BOOK " + bookId + " ALREADY BORROWED BY USER " + userId);
      }
    });

    // TODO: Perform book borrowing
    BookBorrow newBorrow = this.borrowRepo.save(new BookBorrow(null, LocalDateTime.now(), user, book));
    return newBorrow.getId();
  }
}
