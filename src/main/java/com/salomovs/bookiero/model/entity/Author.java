package com.salomovs.bookiero.model.entity;

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

@AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="authors") @Getter @Setter
public class Author {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(name="full_name", nullable=false)
  private String fullName;

  @Column(name="profile_picture", nullable=false)
  private String profilePicture;
}
