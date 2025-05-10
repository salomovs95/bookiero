package com.salomovs.bookiero.mapper;

import com.salomovs.bookiero.model.entity.Book;
import com.salomovs.bookiero.view.dto.BookData;

public class BookMapper {
  public static BookData mapBookToData(Book book, Long borrowCount) {
    return new BookData(
      book.getId(),
      book.getTitle(),
      book.getEsbn(),
      book.getEdition(),
      book.getCategory(),
      book.getEditor(),
      book.getPageCount(),
      book.getPublishYear(),
      book.getInStockAmount(),
      borrowCount,
      book.getAuthor()
    );
  }
}
