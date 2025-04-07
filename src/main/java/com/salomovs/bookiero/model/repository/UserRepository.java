package com.salomovs.bookiero.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {}
