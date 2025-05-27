package com.salomovs.bookiero.model.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.salomovs.bookiero.model.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @NativeQuery("""
    SELECT * 
    FROM users u 
    WHERE u.username = :credential 
    OR u.email = :credential
  """)
  public Optional<User> findByUsernameOrEmail(@Param("credential") String credential);
}
