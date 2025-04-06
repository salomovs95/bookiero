package com.salomovs.bookiero.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.db.entity.BookBorrow;

@Repository
public interface BookBorrowRepository extends CrudRepository<BookBorrow, Integer> {}
