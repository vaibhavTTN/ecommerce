package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.Seller;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(
                    name = "user_id",referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",referencedColumnName = "id"
            )
    )
    private Set<Role> role = new HashSet<>();

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Seller seller;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
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

    @Column(columnDefinition = "int default 0")
    private Integer invalidAttemptCount;

    @NotNull
    @Column(nullable = false)
    private String passwordUpdatedDate;

        public void addAddress(Address address1){
        if(address1!=null){
            if(address == null){
                address = new ArrayList<>();
            }
            address1.setUser(this);
            address.add(address1);
        }
    }

}
