package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@SQLDelete(sql = "UPDATE table_product SET isDeleted = true WHERE id=?")
//@Where(clause = "isDeleted=false")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Role role;

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  private List<Address> address;

  @Column(unique = true, updatable = false)
  private String email;

  @Column(nullable = false)
  private String firstName;

  private String middleName;

  @Column(nullable = false)
  private String lastName;

  @Column(nullable = false)
  private String password;

  private String modifiedBy;

  private String createdBy;

  private LocalDateTime createdAt;

  private LocalDateTime modifiedAt;

  private Boolean isDelete = Boolean.FALSE;

  private Boolean isActive=Boolean.FALSE;

  private Boolean isExpired=Boolean.FALSE;

  private Boolean isLocked=Boolean.FALSE;

  private int invalidAttemptCount;

  @Column(nullable = true)
  private Date lockTime;

  @NotNull
  @Column(nullable = false)
  private LocalDateTime passwordUpdatedDate;

}
