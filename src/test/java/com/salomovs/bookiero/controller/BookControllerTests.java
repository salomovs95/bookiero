package com.salomovs.bookiero.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.salomovs.bookiero.exception.BookNotFoundException;
import com.salomovs.bookiero.model.repository.BookRepository;
import com.salomovs.bookiero.view.dto.CreateBookDto;

@Tag("BOOKS")
@SpringBootTest @TestInstance(Lifecycle.PER_CLASS)
public class BookControllerTests {
  @Autowired
  private BookRepository bRepo;
  private BookController bController;

  @BeforeAll
  public void setup() {
    bController = new BookController(bRepo);
  }

  @Test
  public void CreateBookSuccess() {
    CreateBookDto dto = new CreateBookDto("title", "authorName", 2, "category", "esb", "edition", "editor", 2025, 1);
    assertDoesNotThrow(()->bController.create(dto));
  }

  @Test
  public void CreateBookFailNullDto() {
    assertThrows(NullPointerException.class, ()->bController.create(null));
  }

  @Test
  public void CreateBookFailInvalidDto() {
    CreateBookDto dto = new CreateBookDto(null, null, null, null, null, null, null, null, null);
    assertThrows(Exception.class, ()->bController.create(dto));
  }

  @Test
  public void FindBookSuccess() {
    CreateBookDto dto = new CreateBookDto("title", "authorName", 2, "category", "esb", "edition", "editor", 2025, 1);
    assertDoesNotThrow(()->bController.create(dto));
    assertDoesNotThrow(()->bController.findBookById(1));
  }

  @Test
  public void FindBookFail() {
    assertThrows(BookNotFoundException.class, ()->bController.findBookById(0));
  }
}
