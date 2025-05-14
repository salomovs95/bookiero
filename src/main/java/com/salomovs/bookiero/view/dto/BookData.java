package com.salomovs.bookiero.view.dto;


import com.salomovs.bookiero.model.entity.Author;
import com.salomovs.bookiero.model.entity.Book;

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
  String bookCover,
  Author author
) {
  public static BookData from(Book book, Long borrowCount) {
    return new BookData(book.getId(), book.getTitle(), book.getEsbn(), book.getEdition(), book.getCategory(), book.getEditor(), book.getPageCount(), book.getPublishYear(), book.getInStockAmount(), borrowCount, book.getBookCover(), book.getAuthor());
  }
}
