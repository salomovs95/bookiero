package com.salomovs.bookiero.view;

import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
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
import com.salomovs.bookiero.view.dto.BookData;
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
  public ResponseEntity<String> createBooks(@RequestBody @Valid CreateBookDto body) {
    Integer newBookId = this.bookController.create(body);
    return ResponseEntity.status(201).body(String.format("{\"id\": %d}", newBookId));
  }

  @GetMapping("/")
  public ResponseEntity<List<BookData>> listAllBooks() {
    List<BookData> responseBody = this.mapDataBatch(this.bookController.listBook());
    return ResponseEntity.status(200).body(responseBody);
  }

  @GetMapping("/{book_id}")
  public ResponseEntity<BookData> findSpecificBook(@PathVariable(name="book_id") Integer bookId) {
    Book book = this.bookController.findBookById(bookId);
    Long activeBorrows = this.borrowController.countActiveBorrows(book.getId());
    
    return ResponseEntity.status(200).body(this.mapData(book, activeBorrows));
  }

  @PostMapping("/borrows/{book_id}/{user_id}")
  public ResponseEntity<String> borrowABook(@PathVariable(name="book_id") Integer bookId,
                                       @PathVariable(name="user_id") Integer userId) {
    User user = this.authController.getUserInfo(userId);
    Book book = this.bookController.findBookById(bookId);
    
    int newBorrowId = this.borrowController.borrowBook(book, user);
    return ResponseEntity.status(201).body(String.format("{\"ok\": true, \"id\": %d}", newBorrowId));
  }

  @PatchMapping("/borrows/{borrow_id}/return")
  public ResponseEntity<String> returnBook(@PathVariable(name="borrow_id") Integer borrowId) {
    this.borrowController.returnBook(borrowId);
    return ResponseEntity.status(200).body("{\"ok\": true }");
  }

  private List<BookData> mapDataBatch(Iterable<Book> books) {
    List<BookData> bDL = new ArrayList<>();
    books.forEach((book) -> {
      BookData data = this.mapData(book, this.borrowController.countActiveBorrows(book.getId()));
      bDL.add(data);
    });
    return bDL;
  }

  private BookData mapData(Book book, Long activeBorrows) {
    BookData bookData = new BookData(
      book.getId(),
      book.getTitle(),
      book.getEsbn(),
      book.getEdition(),
      book.getCategory(),
      book.getAuthorName(),
      book.getEditor(),
      book.getPageCount(),
      book.getPublishYear(),
      book.getInStockAmount(),
      activeBorrows
    );
    return bookData;
  }
}
