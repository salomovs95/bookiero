package com.salomovs.bookiero.view.dto;

import java.time.LocalDate;

public interface ReturnsAnalyticDTO {
  LocalDate getReturnedAt();
  Long getQuantity();
}
