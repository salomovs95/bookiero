package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
  @Query(
    value="""
      SELECT authors.id, authors.full_name, authors.profile_picture, 
      (
        SELECT COUNT(borrows.id) 
        FROM borrows 
        JOIN books 
        WHERE borrows.book_id = books.id 
        AND books.author_id = authors.id
      ) AS borrow_count 
      FROM authors 
      WHERE 
      (
        SELECT COUNT(borrows.id) 
        FROM borrows 
        JOIN books 
        WHERE borrows.book_id = books.id 
        AND books.author_id = authors.id
      ) > 0 
      GROUP BY authors.id 
      ORDER BY borrow_count DESC 
      LIMIT 3
    """,
    nativeQuery=true
  )
  List<Author> retrieveMostPopularAuthors();
}
