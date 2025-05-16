package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
  @Query(
    value="SELECT books.*, MAX((SELECT COUNT(id) FROM borrows br WHERE br.book_id = books.id)) borrow_count FROM books GROUP BY id ORDER BY borrow_count DESC LIMIT 3",
    nativeQuery=true
  )
  List<Book> retrieveMostBorrowsBooks();
}
