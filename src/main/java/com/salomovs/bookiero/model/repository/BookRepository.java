package com.salomovs.bookiero.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.db.entity.Book;

@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {}
