package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.BookBorrow;

@Repository
public interface BookBorrowRepository extends JpaRepository<BookBorrow, Integer> {
  @Query(
    nativeQuery=true, 
    value="""
      SELECT * FROM borrows b 
      WHERE b.book_id = :book_id 
      AND b.returned_at IS NULL
    """
  )
  List<BookBorrow> findByBookId(@Param("book_id") Integer bookId);

  @Query(
    nativeQuery=true,
    value="""
      SELECT COUNT(id) FROM borrows b 
      WHERE b.book_id = :book_id 
      AND b.returned_at IS NULL
    """
  )
  Long countActiveBorrows(@Param("book_id") Integer bookId);
}
