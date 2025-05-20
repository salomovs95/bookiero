package com.salomovs.bookiero.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

import com.salomovs.bookiero.exception.AuthorNotFoundException;
import com.salomovs.bookiero.exception.BookNotFoundException;
import com.salomovs.bookiero.model.repository.AuthorRepository;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.view.dto.CreateBookDto;
import com.salomovs.bookiero.view.dto.RegisterAuthorDTO;

@Tag("BOOKS")
@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
@Sql(
  scripts={"/borrow.sql"},
  executionPhase=ExecutionPhase.BEFORE_TEST_CLASS
)
public class BookControllerTests {
  @Autowired
  private BookRepository bRepo;

  @Autowired
  private AuthorRepository authorRepo;
  
  private BookController bController;

  @BeforeAll
  public void setup() {
    bController = new BookController(authorRepo, bRepo);
  }

  @Test
  public void CreateBookSuccess() {
    CreateBookDto dto = new CreateBookDto("title", 999, "category", "esbn", "edition", "editor", 2025, 1l, Optional.of("bookCover"), 95);
    assertDoesNotThrow(()->bController.create(dto));
  }

  @Test
  public void CreateBookFailNullDto() {
    assertThrows(NullPointerException.class, ()->bController.create(null));
  }

  @Test
  public void CreateBookFailInvalidDto() {
    CreateBookDto dto = new CreateBookDto(null, null, null, null, null, null, null, null, null, null);
    assertThrows(Exception.class, ()->bController.create(dto));
  }

  @Test
  public void FindBookSuccess() {
    assertDoesNotThrow(()->bController.findBookById(11));
  }

  @Test
  public void FindBookFail() {
    assertThrows(BookNotFoundException.class, ()->bController.findBookById(0));
  }

  @Test
  public void RegisterAuthorSuccess() {
    RegisterAuthorDTO dto = new RegisterAuthorDTO("Author 001", "author-001-pic");
    assertDoesNotThrow(()->bController.registerAuthor(dto));
  }

  @Test
  public void RegisterAuthorFail() {
    RegisterAuthorDTO dto = new RegisterAuthorDTO(null, null);
    assertThrows(Exception.class, ()->bController.registerAuthor(dto));
  }

  @Test
  public void RegisterAuthorFailNullDto() {
    assertThrows(NullPointerException.class, ()->bController.registerAuthor(null));
  }

  @Test
  public void FindAuthorSuccess() {
    int authorId = 95;
    assertDoesNotThrow(()->bController.findAuthor(authorId));
  }

  @Test
  public void FindAuthorFail() {
    int authorId = 999;
    assertThrows(AuthorNotFoundException.class, ()->bController.findAuthor(authorId));
  }
}
