package com.salomovs.bookiero.view;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.controller.BookController;
import com.salomovs.bookiero.view.dto.CreateBookDto;

@RestController @RequestMapping("/api/books")
public class BooksView {
  private BookController bookController;

  public BooksView(final BookController bookController) {
    this.bookController = bookController;
  }

  @PostMapping("/")
  public ResponseEntity<Integer> createBooks(@RequestBody @Valid CreateBookDto body) {
    Integer newBookId = this.bookController.create(body);
    return ResponseEntity.status(201).body(newBookId);
  }
}
