package com.salomovs.bookiero.db.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
  private Integer id;

  @Column(name="full_name", nullable=false)
  private String fullName;

  @Column(name="tax_id", nullable=false)
  private String taxId;

  @Column(nullable=false)
  private String password;

  @Column(nullable=false)
  private String email;
  
  @Column(nullable=false)
  private String phone;

  @Column(nullable=false)
  private String address;
}
