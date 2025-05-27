package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.BookBorrow;
import com.salomovs.bookiero.view.dto.BorrowAnalyticDTO;
import com.salomovs.bookiero.view.dto.ReturnsAnalyticDTO;

@Repository
public interface BookBorrowRepository extends JpaRepository<BookBorrow, Integer> {
  @NativeQuery("""
    SELECT * FROM borrows b 
    WHERE b.book_id = :book_id 
    AND b.returned_at IS NULL
  """)
  List<BookBorrow> findByBookId(@Param("book_id") Integer bookId);

  @NativeQuery("""
    SELECT COUNT(id) FROM borrows b 
    WHERE b.book_id = :book_id 
    AND b.returned_at IS NULL
  """)
  Long countActiveBorrows(@Param("book_id") Integer bookId);

  @NativeQuery("""
    SELECT b.borrowed_at as borrowedAt, 
    COUNT(id) as quantity 
    FROM borrows b 
    WHERE b.borrowed_at >= (CURRENT_DATE - :limit) 
    GROUP BY b.borrowed_at
  """)
  List<BorrowAnalyticDTO> getLastBorrows(@Param("limit") Integer limitDays);
  
  @NativeQuery("""
    SELECT b.returned_at AS returnedAt, 
    COUNT(b.id) AS quantity 
    FROM borrows b 
    WHERE b.returned_at IS NOT NULL 
    AND b.returned_at >= (CURRENT_DATE - :limit) 
    GROUP BY b.id
  """)
  List<ReturnsAnalyticDTO> getLastReturns(@Param("limit") Integer limitDays);
}
