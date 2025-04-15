package com.salomovs.bookiero.view.dto;

public record BookData(
  Integer id,
  String title,
  String esbn,
  String edition,
  String category,
  String authorName,
  String editor,
  Integer pageCount,
  Integer publishYear,
  Integer inStockAmount,
  Long borrowCount
) {}
