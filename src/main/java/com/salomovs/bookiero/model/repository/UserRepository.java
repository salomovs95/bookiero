package com.salomovs.bookiero.db.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.db.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {}
