package com.salomovs.bookiero.view;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salomovs.bookiero.annotation.ApiOperation;
import com.salomovs.bookiero.controller.AuthController;
import com.salomovs.bookiero.controller.BookController;
import com.salomovs.bookiero.controller.BorrowController;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsView {
  private AuthController authController;
  private BookController bookController;
  private BorrowController borrowController;

  public AnalyticsView(final AuthController authController,
                       final BookController bookController,
                       BorrowController borrowController) {
    this.authController = authController;
    this.bookController = bookController;
    this.borrowController = borrowController;
  }


  @ApiOperation(summary="Get Metrics and Analytics", security="jwt")
  @GetMapping("")
  public ResponseEntity<Map<String, Object>> getAnalytics(@RequestParam(name="limit") Optional<String> limitDays) {
    Map<String, Object> map = new HashMap<>();

    map.put("users_count", this.authController.countUsers());
    map.put("books_count", this.bookController.countBooks());
    map.put("authors_count", this.bookController.countAuthors());

    Map<String, Long> lastBorrows = new HashMap<>();

    this.borrowController
        .getLastBorrows(limitDays.orElse("7"))
        .stream()
        .forEach(b->lastBorrows.put(
           b.getBorrowedAt().toString(),
           b.getQuantity()
        ));
    map.put("last_borrows", lastBorrows);
   
    Map<String, Long> lastReturns = new HashMap<>();

    this.borrowController
        .getLastReturns(limitDays.orElse("7"))
        .stream()
        .forEach(r->lastReturns.put(
           r.getReturnedAt().toString(),
           r.getQuantity()
        ));
    map.put("last_returns", lastReturns);

    return ResponseEntity.status(200).body(map);
  }
}
