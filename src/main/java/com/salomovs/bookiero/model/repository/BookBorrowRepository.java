package com.salomovs.bookiero.model.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.BookBorrow;

@Repository
public interface BookBorrowRepository extends CrudRepository<BookBorrow, Integer> {
  List<BookBorrow> findAllByBookId(Integer bookId);
}
