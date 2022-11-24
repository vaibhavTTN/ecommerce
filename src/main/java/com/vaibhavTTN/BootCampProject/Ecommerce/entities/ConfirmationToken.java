package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfirmationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true)
  private String token;

  @Column(updatable = false)
  @Basic(optional = false)
  private LocalDateTime expireAt;

  @OneToOne
  private User user;

  @Transient
  private boolean isExpired;

  public ConfirmationToken(User user, LocalDateTime Time) {
    this.token = UUID.randomUUID().toString();
    this.expireAt = Time;
    this.user = user;
  }

  public boolean isExpired() {
    return getExpireAt().isBefore(LocalDateTime.now());
  }
}
