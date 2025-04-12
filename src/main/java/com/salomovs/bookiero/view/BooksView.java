package com.salomovs.bookiero.view;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.controller.BookController;
import com.salomovs.bookiero.controller.BorrowController;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.CreateBookDto;

@RestController @RequestMapping("/api/books")
public class BooksView {
  private AuthController authController;
  private BookController bookController;
  private BorrowController borrowController;

  public BooksView(final AuthController authController,
                   final BookController bookController,
                   final BorrowController borrowController) {
    this.authController = authController;
    this.bookController = bookController;
    this.borrowController = borrowController;
  }

  @PostMapping("/")
  public ResponseEntity<Integer> createBooks(@RequestBody @Valid CreateBookDto body) {
    Integer newBookId = this.bookController.create(body);
    return ResponseEntity.status(201).body(newBookId);
  }

  @GetMapping("/")
  public ResponseEntity<Iterable<Book>> listAllBooks() {
    Iterable<Book> books = this.bookController.listBook();
    return ResponseEntity.status(200).body(books);
  }

  @GetMapping("/{book_id}")
  public ResponseEntity<Book> findSpecificBook(@PathVariable(name="book_id") Integer bookId) {
    Book book = this.bookController.findBookById(bookId);
    return ResponseEntity.status(200).body(book);
  }

  @PostMapping("/borrow/{book_id}/{user_id}")
  public ResponseEntity<?> borrowABook(@PathVariable(name="book_id") Integer bookId,
                                       @PathVariable(name="user_id") Integer userId) {
    User user = authController.getUserInfo(userId);
    Book book = bookController.findBookById(bookId);
    
    this.borrowController.borrowBook(book, user);
    return ResponseEntity.status(201).body("{\"ok\":\"true\"}");
  }
}
