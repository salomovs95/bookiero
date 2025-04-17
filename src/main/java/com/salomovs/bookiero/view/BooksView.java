package com.salomovs.bookiero.view;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

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
import com.salomovs.bookiero.mapper.BookMapper;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.BookData;
import com.salomovs.bookiero.view.dto.CreateBookDto;
import com.salomovs.bookiero.view.dto.HttpResponse;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequiredArgsConstructor
@Tag(name="Books View", description="Handles operations on books and borrowing")
@RestController @RequestMapping("/api/books")
public class BooksView {
  private AuthController authController;
  private BookController bookController;
  private BorrowController borrowController;

  @Operation(summary="Book Creation Handler") @PostMapping("/")
  @ApiResponses(
    @ApiResponse(
      responseCode="201",
      content=@Content(
        mediaType="application/json",
        schema=@Schema(
          implementation=HttpResponse.class,
          example="{\"ok\": true, \"id\": 0}"
        )
      )
    )
  )
  public ResponseEntity<HttpResponse> createBooks(@RequestBody @Valid CreateBookDto body) {
    Integer newBookId = this.bookController.create(body);
    return ResponseEntity.status(201).body(new HttpResponse(true, newBookId));
  }

  @Operation(summary="Book Listing Handler") @GetMapping("/")
  @ApiResponses(
    @ApiResponse(
      responseCode="200",
      content=@Content(
        mediaType="application/json",
        schema=@Schema(
          implementation=List.class,
          example="[]"
        )
      )
    )
  )
  public ResponseEntity<List<BookData>> listAllBooks() {
    List<BookData> responseBody = this.bookController
      .listBook()
      .stream()
      .map(b->BookMapper.mapBookToData(
         b,
         this.borrowController.countActiveBorrows(b.getId())
      ))
      .collect(Collectors.toList());
    return ResponseEntity.status(200).body(responseBody);
  }

  @Operation(summary="Book Specific Info Handler") @GetMapping("/{book_id}")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      content=@Content(
        mediaType="application/json",
        schema=@Schema(
          implementation=BookData.class,
          example="{\"id\": 0, \"title\":\"the millenium bug\", \"authorName\":\"salomovs\", \"category\":\"sci-fi\", \"esbn\":\"esbn-number-here\", \"edition\":\"1st\", \"editor\":\"any\", \"pageCount\":9999, \"publishYear\": 2025, \"inStockAmount\":999, \"borrowCount\": 0}"
        )
      )
    )
  })
  public ResponseEntity<BookData> findSpecificBook(@PathVariable(name="book_id") Integer bookId) {
    Book book = this.bookController.findBookById(bookId);
    Long activeBorrows = this.borrowController.countActiveBorrows(book.getId());
    
    return ResponseEntity.status(200).body(BookMapper.mapBookToData(book, activeBorrows));
  }

  @Operation(summary="Book Borrowing Handler") @PostMapping("/borrows/{book_id}/{user_id}")
  @ApiResponses({
    @ApiResponse(
      responseCode="201",
      content=@Content(
        mediaType="application/json",
        schema=@Schema(
          implementation=HttpResponse.class,
          example="{\"ok\": true, \"id\": 0}"
        )
      )
    )
  })
  public ResponseEntity<HttpResponse> borrowABook(@PathVariable(name="book_id") Integer bookId,
                                                  @PathVariable(name="user_id") Integer userId) {
    User user = this.authController.getUserInfo(userId);
    Book book = this.bookController.findBookById(bookId);
    
    int newBorrowId = this.borrowController.borrowBook(book, user);
    return ResponseEntity.status(201).body(new HttpResponse(true , newBorrowId));
  }

  @Operation(summary="Book Return Handler") @PatchMapping("/borrows/{borrow_id}/return")
  @ApiResponses({
    @ApiResponse(
      responseCode="200",
      content=@Content
    )
  })
  public ResponseEntity<String> returnBook(@PathVariable(name="borrow_id") Integer borrowId) {
    this.borrowController.returnBook(borrowId);
    return ResponseEntity.status(200).build();
  }
}
