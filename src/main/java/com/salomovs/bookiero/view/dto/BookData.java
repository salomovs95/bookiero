package com.salomovs.bookiero.view.dto;

import com.salomovs.bookiero.model.entity.Author;

public record BookData(
  Integer id,
  String title,
  String esbn,
  String edition,
  String category,
  String editor,
  Integer pageCount,
  Integer publishYear,
  Integer inStockAmount,
  Long borrowCount,
  Author author
) {}
