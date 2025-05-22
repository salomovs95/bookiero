package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.BookBorrow;
import com.salomovs.bookiero.view.dto.BorrowAnalyticDTO;
import com.salomovs.bookiero.view.dto.ReturnsAnalyticDTO;

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

  @Query(
    nativeQuery=true,
    value="""
      SELECT b.borrowed_at as borrowedAt, COUNT(id) as quantity 
      FROM borrows b 
      WHERE b.borrowed_at >= CURRENT_TIMESTAMP - INTERVAL :limitDays DAY 
      GROUP BY b.borrowed_at
    """
  )
  List<BorrowAnalyticDTO> getLastBorrows(@Param("limitDays") String limitDays);
  
  @Query(
    nativeQuery=true,
    value="""
      SELECT b.returned_at as returnedAt, COUNT(id) as quantity 
      FROM borrows b 
      WHERE NOT b.returned_at = NULL 
      AND b.returned_at >= CURRENT_TIMESTAMP - INTERVAL :limitDays DAY 
      GROUP BY b.returned_at
    """
  )
  List<ReturnsAnalyticDTO> getLastReturns(@Param("limitDays") String limitDays);
}
