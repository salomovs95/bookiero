package com.salomovs.bookiero.view;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.annotation.ApiOperation;
import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.controller.BookController;
import com.salomovs.bookiero.controller.BorrowController;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.BookData;
import com.salomovs.bookiero.view.dto.CreateBookDto;
import com.salomovs.bookiero.view.dto.HttpResponse;

@Tag(name="Books View", description="Handles operations on books and borrowing")
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

  @ApiOperation(summary="Book Creation Handler", security="jwt")
  @PostMapping("/")
  @PreAuthorize("hasRole('USER_ADMIN')")
  public ResponseEntity<HttpResponse> registerBook(@RequestBody @Valid CreateBookDto body) {
    Integer bookId = this.bookController.create(body);
    return ResponseEntity.status(201).body(new HttpResponse(true, String.format("{ \"id\": %d }", bookId)));
  }

  @ApiOperation(summary="Book Listing Handler", security="jwt")
  @GetMapping("/")
  public ResponseEntity<List<BookData>> listAllBooks() {
    List<BookData> responseBody = this.bookController
      .listBook()
      .stream()
      .map(b->BookData.from(
         b,
         this.borrowController.countActiveBorrows(b.getId())
      ))
      .collect(Collectors.toList());
    return ResponseEntity.status(200).body(responseBody);
  }

  @ApiOperation(summary="Book Specific Info Handler", security="jwt")
  @GetMapping("/{book_id}")
  public ResponseEntity<HttpResponse> findSpecificBook(@PathVariable(name="book_id") Integer bookId) {
    Book book = this.bookController.findBookById(bookId);
    Long activeBorrows = this.borrowController.countActiveBorrows(book.getId());
    
    return ResponseEntity.status(200).body(new HttpResponse(true, BookData.from(book, activeBorrows)));
  }

  @ApiOperation(summary="Book Borrowing Handler",security="jwt")
  @PostMapping("/borrows/{book_id}/{user_id}")
  public ResponseEntity<HttpResponse> borrowABook(@PathVariable(name="book_id") Integer bookId,
                                                  @PathVariable(name="user_id") Integer userId) {
    User user = this.authController.getUserInfo(userId);
    Book book = this.bookController.findBookById(bookId);
    
    int newBorrowId = this.borrowController.borrowBook(book, user);
    return ResponseEntity.status(201).body(new HttpResponse(true , newBorrowId));
  }

  @ApiOperation(summary="Book Return Handler", security="jwt")
  @PatchMapping("/borrows/{borrow_id}/return")
  public ResponseEntity<HttpResponse> returnBook(@PathVariable(name="borrow_id") Integer borrowId) {
    this.borrowController.returnBook(borrowId);
    return ResponseEntity.status(200).body(new HttpResponse(true, null));
  }
}
