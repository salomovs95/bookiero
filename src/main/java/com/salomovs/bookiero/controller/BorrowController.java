package com.salomovs.bookiero.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.salomovs.bookiero.exception.BookBorrowingException;
import com.salomovs.bookiero.exception.BorrowNotFoundException;
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
    List<BookBorrow> borrows = this.borrowRepo.findByBookId(book.getId());
    if (borrows.size() >= book.getInStockAmount()) {
      throw new BookBorrowingException("BOOK NOT AVAILABLE TO BORROW");
    }

    borrows.stream().forEach(b->{
      if (b.getWhoBorrows().getId().equals(user.getId())) {
        throw new BookBorrowingException("BOOK " + book.getId() + " ALREADY BORROWED BY USER " + user.getId());
      }
    });

    BookBorrow newBorrow = this.borrowRepo.save(new BookBorrow(null, LocalDateTime.now(), user, book));
    return newBorrow.getId();
  }

  public void returnBook(Integer borrowId) {
    BookBorrow borrow = this.borrowRepo.findById(borrowId)
      .orElseThrow(()->new BorrowNotFoundException("BORROW NOT FOUND"));
    borrow.setReturnedAt(LocalDateTime.now());
    this.borrowRepo.save(borrow);
  }

  public Long countActiveBorrows(Integer bookId) {
    long borrowCount = this.borrowRepo.countActiveBorrows(bookId);
    return borrowCount;
  }
}
