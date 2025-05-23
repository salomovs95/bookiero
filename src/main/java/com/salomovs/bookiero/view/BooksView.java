package com.salomovs.bookiero.view;

import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.annotation.ApiOperation;
import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.controller.BookController;
import com.salomovs.bookiero.controller.BorrowController;
import com.salomovs.bookiero.model.entity.Author;
import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.model.entity.User;
import com.salomovs.bookiero.view.dto.BookData;
import com.salomovs.bookiero.view.dto.BorrowBookDTO;
import com.salomovs.bookiero.view.dto.CreateBookDto;
import com.salomovs.bookiero.view.dto.RegisterAuthorDTO;

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
  @PostMapping("")
  public ResponseEntity<Map<String, Integer>> registerBook(@RequestBody @Valid CreateBookDto body) {
    Integer bookId = this.bookController.create(body);

    Map<String, Integer> map = new HashMap<>();
    map.put("book_id", bookId);

    return ResponseEntity.status(201).body(map);
  }

  @ApiOperation(summary="Top Books Ranking", security="")
  @GetMapping("/ranking")
  public ResponseEntity<List<BookData>> getMostBorrowedBooks() {
    List<BookData> responseBody = this.bookController
      .listMostBorrowedBooks()
      .stream()
      .map(b->(
        BookData.from(b, this.borrowController.countActiveBorrows(b.getId()))
      ))
      .collect(Collectors.toList());
    
    return ResponseEntity.status(200).body(responseBody);
  }

  @ApiOperation(summary="List Books Paginated", security="jwt")
  @GetMapping("")
  public ResponseEntity<Map<String, Object>> getPaginatedBooks(@RequestParam(name="page") Optional<Integer> page,
                                                               @RequestParam(name="search") Optional<String> search) {
    Integer pageNumber = page.orElse(0);
    String predicate = search.orElse("");

    Slice<Book> slice = this.bookController
                            .paginateBooks(predicate, pageNumber);
    
    Map<String,Object> map = new HashMap<>();
    map.put("page_number", pageNumber);
    map.put("has_next", slice.hasNext());

    map.put("book_list", slice.stream().map(b->(
      BookData.from(
        b,
        this.borrowController.countActiveBorrows(b.getId())
      )
    )).collect(Collectors.toList()));

    return ResponseEntity.status(200).body(map);
  }

  @ApiOperation(summary="Book Specific Info Handler", security="jwt")
  @GetMapping("/{book_id}")
  public ResponseEntity<BookData> findSpecificBook(@PathVariable(name="book_id") Integer bookId) {
    Book book = this.bookController
                    .findBookById(bookId);

    Long activeBorrows = this.borrowController
                             .countActiveBorrows(book.getId());

    return ResponseEntity.status(200).body(BookData.from(book, activeBorrows));
  }

  @ApiOperation(summary="Book Borrowing Handler",security="jwt")
  @PostMapping("/borrows")
  public ResponseEntity<Map<String, Integer>> borrowABook(@RequestBody BorrowBookDTO body) {
    User user = this.authController
                    .getUserInfo(body.userId());
    Book book = this.bookController
                    .findBookById(body.bookId());    
    int newBorrowId = this.borrowController
                          .borrowBook(book, user);

    Map<String, Integer> map = new HashMap<>();
    map.put("borrow_id", newBorrowId);
    
    return ResponseEntity.status(201).body(map);
  }

  @ApiOperation(summary="Book Return Handler", security="jwt")
  @PatchMapping("/borrows/{borrow_id}/return")
  public ResponseEntity<Map<String, Boolean>> returnBook(@PathVariable(name="borrow_id") Integer borrowId) {
    this.borrowController
        .returnBook(borrowId);
    
    Map<String, Boolean> map = new HashMap<>();
    map.put("ok", true);

    return ResponseEntity.status(200).body(map);
  }

  @ApiOperation(summary="Author Registration", security="jwt")
  @PostMapping("/authors")
  public ResponseEntity<Map<String, Integer>> registerAuthor(@RequestBody @Valid RegisterAuthorDTO body) {
    int authorId = this.bookController
                       .registerAuthor(body);
    
    Map<String, Integer> map = new HashMap<>();
    map.put("author_id", authorId);
    
    return ResponseEntity.status(201).body(map);
  }

  @ApiOperation(summary="Top Authors Ranking", security="")
  @GetMapping("/authors/ranking")
  public ResponseEntity<List<Author>> getMostPopularAuthors() {
    List<Author> authors = this.bookController
                               .listMostPopularAuthors();
    
    return ResponseEntity.status(200).body(authors);
  }
}
