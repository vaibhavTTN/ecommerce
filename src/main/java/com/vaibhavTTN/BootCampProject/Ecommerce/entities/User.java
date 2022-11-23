package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Role role;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
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

    @Column(columnDefinition = "boolean default false")
    private Boolean isDelete;

    private String modifiedBy;

    private String createdBy;

    private LocalDateTime createdOn;

    private LocalDateTime modifiedOn;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;

    @Column(columnDefinition = "boolean default false")
    private Boolean isExpired;

    @Column(columnDefinition = "boolean default false")
    private Boolean isLocked;

    private int invalidAttemptCount;

    @Column(nullable = true)
    private Date lockTime;


    @NotNull
    @Column(nullable = false)
    private LocalDateTime passwordUpdatedDate;

}
