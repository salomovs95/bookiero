package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.Author;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Integer> {
  @NativeQuery("""
    SELECT authors.id, authors.full_name, authors.profile_picture 
    FROM authors 
    RIGHT JOIN (SELECT COUNT(br.id) AS bcount 
    FROM borrows br, books bk, authors a 
      WHERE br.book_id = bk.id 
      AND bk.author_id = a.id
    ) AS c ON c.bcount > 0 
    WHERE c.bcount > 0 
    GROUP BY authors.id, c.bcount
    ORDER BY c.bcount 
    LIMIT 3
  """)
  List<Author> retrieveMostPopularAuthors();
}
