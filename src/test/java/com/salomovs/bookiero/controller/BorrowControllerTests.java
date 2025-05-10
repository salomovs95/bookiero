package com.salomovs.bookiero.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.bookiero.exception.BookBorrowingException;
import com.salomovs.bookiero.exception.BorrowNotFoundException;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.model.repository.AuthorRepository;
import com.salomovs.bookiero.model.repository.BookBorrowRepository;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.model.repository.UserRepository;

@Tag("BORROWING")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Sql(
  scripts={"/borrow.sql"},
  executionPhase=ExecutionPhase.BEFORE_TEST_CLASS
)
public class BorrowControllerTests {
  @Autowired
  private UserRepository userRepo;
  private AuthController aController;

  @Autowired
  private AuthorRepository authorRepo;

  @Autowired
  private BookRepository bookRepo;
  private BookController bController;

  @Autowired
  private BookBorrowRepository borrowRepo;
  private BorrowController bbController;

  @BeforeAll
  private void setup() {
    aController = new AuthController(userRepo, null);
    bController = new BookController(authorRepo, bookRepo);
    bbController = new BorrowController(borrowRepo);
  }

  @AfterEach
  public void undoSetup() {
    borrowRepo.deleteAll();
  }

  @Test @Order(1)
  public void BorrowBookSuccess() {
    assertDoesNotThrow(()->{
      User user = aController.getUserInfo(61);
      Book book = bController.findBookById(11);
    
      assertDoesNotThrow(()->bbController.borrowBook(book, user));
    });
  }

  @Test @Order(2)
  public void BorrowBookFailUserBorrowBookTwice() {
    bbController.borrowBook(bController.findBookById(11),aController.getUserInfo(61));

    BookBorrowingException e = assertThrows(BookBorrowingException.class, ()->{
      bbController.borrowBook(
        bController.findBookById(11),
        aController.getUserInfo(61)
      );
    });

    assertEquals("BOOK 11 ALREADY BORROWED BY USER 61", e.getMessage());
  }

  @Test @Order(3)
  public void BorrowBookFailUserBookNotAvailable() {
    Book book = bController.findBookById(11);
    bbController.borrowBook(book, aController.getUserInfo(61));
    bbController.borrowBook(book, aController.getUserInfo(62));

    BookBorrowingException e = assertThrows(BookBorrowingException.class, ()->{
      bbController.borrowBook(book, aController.getUserInfo(63));
    });

    assertEquals("BOOK NOT AVAILABLE TO BORROW", e.getMessage());
  }

  @Test @Order(4)
  public void ReturnBorrowedBookSuccess() {
    Book book = bController.findBookById(11);
    User user = aController.getUserInfo(63);
    Integer borrowId = bbController.borrowBook(book, user);

    assertDoesNotThrow(()->bbController.returnBook(borrowId));
  }

  @Test @Order(5)
  public void ReturnBorrowedBookFail() {
    Integer borrowId = 999;
    BorrowNotFoundException e = assertThrows(BorrowNotFoundException.class, ()->bbController.returnBook(borrowId));
    assertEquals("BORROW NOT FOUND", e.getMessage());
  }
}
