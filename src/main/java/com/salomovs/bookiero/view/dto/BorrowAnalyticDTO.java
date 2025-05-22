package com.salomovs.bookiero.view.dto;

import java.time.LocalDate;

public interface BorrowAnalyticDTO {
  LocalDate getBorrowedAt();
  Long getQuantity();
}
