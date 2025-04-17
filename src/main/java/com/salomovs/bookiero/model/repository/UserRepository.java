package com.salomovs.bookiero.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {}
