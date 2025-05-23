package com.salomovs.bookiero.model.entity;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

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

import com.salomovs.bookiero.enums.Roles;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity @Table(name="users")
public class User implements UserDetails {
  @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Integer id;

  @Column(name="full_name", nullable=false)
  private String fullName;

  @Column(nullable=false, unique=true)
  private String username;

  @Column(name="tax_id", nullable=false, unique=true)
  private String taxId;

  @Column(nullable=false)
  private String password;

  @Column(nullable=false, unique=true)
  private String email;
  

  @Column(nullable=false, unique=true)
  private String phone;

  @Column(nullable=false)
  private String address;

  @Column(nullable=true)
  private String role;

  @Column(name="joined_at", nullable=false)
  private LocalDate joinedAt;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of( new SimpleGrantedAuthority(
        this.getRole() == null ? Roles.USER_COMMON.toString() : this.getRole()
    ) );
  }
}
