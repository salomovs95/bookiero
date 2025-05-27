package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
  @NativeQuery("""
    SELECT books.*, 
    MAX((
      SELECT COUNT(id) 
      FROM borrows br 
      WHERE br.book_id = books.id
    )) borrow_count 
    FROM books 
    GROUP BY id 
    ORDER BY borrow_count DESC 
    LIMIT 3
  """)
  List<Book> retrieveMostBorrowsBooks();

  @NativeQuery("""
    SELECT b.* 
    FROM books b 
    WHERE title LIKE %:predicate% 
    OR category LIKE %:predicate% 
    OR editor LIKE %:predicate% 
    OR (
      SELECT full_name 
      FROM authors a 
      WHERE b.author_id = a.id
    ) 
    LIKE %:predicate%
  """)
  Slice<Book> findByPredicate(@Param("predicate") String predicate,
                              Pageable pageable);
}
