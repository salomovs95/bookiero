package com.salomovs.bookiero.view.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record CreateBookDto(
  @NotNull @NotEmpty String title,
  @NotNull @NotEmpty String authorName,
  @NotNull @Min(10) Integer pageCount,
  @NotNull @NotEmpty String category,
  @NotNull @NotEmpty String esbn,
  @NotNull @NotEmpty String edition,
  @NotNull @NotEmpty String editor,
  @NotNull @Min(1) Integer publishYear
) {}
