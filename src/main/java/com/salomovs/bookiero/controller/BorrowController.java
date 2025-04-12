package com.salomovs.bookiero.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.BookBorrow;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.model.repository.BookBorrowRepository;

@Service
public class BorrowController {
  private BookBorrowRepository borrowRepo;

  public BorrowController(final BookBorrowRepository borrowRepo) {
    this.borrowRepo = borrowRepo;
  }

  public Integer borrowBook(Book book, User user) {
    // TODO: Check book availability
    List<BookBorrow> borrows = this.borrowRepo.findByBookId(book.getId());
    System.out.println("HOW MUCH BORROWS: " + borrows.size());
    if (borrows.size() >= book.getInStockAmount()) {
      throw new RuntimeException("BOOK NOT AVAILABLE TO BORROW");
    }

    // TODO: Check if ueer has already borrowed this book
    borrows.stream().forEach(b->{
      if (b.getWhoBorrows().getId().equals(user.getId())) {
        throw new RuntimeException("BOOK " + book.getId() + " ALREADY BORROWED BY USER " + user.getId());
      }
    });

    // TODO: Perform book borrowing
    BookBorrow newBorrow = this.borrowRepo.save(new BookBorrow(null, LocalDateTime.now(), user, book));
    return newBorrow.getId();
  }
}
