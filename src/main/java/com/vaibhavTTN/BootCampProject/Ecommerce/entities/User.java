package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

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

    private String createdOn;

    private String modifiedOn;

    @Column(columnDefinition = "boolean default false")
    private Boolean isActive;

    @Column(columnDefinition = "boolean default false")
    private Boolean isExpired;

    @Column(columnDefinition = "boolean default false")
    private Boolean isLocked;

    @Column(columnDefinition = "integer default 0")
    private Integer invalidAttemptCount;

    @NotNull
    @Column(nullable = false)
    private String passwordUpdatedDate;

}
