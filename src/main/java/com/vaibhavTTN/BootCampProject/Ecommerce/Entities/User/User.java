package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.Seller;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.List;

@Entity
@Data
public class User {

//    @Id
//    @GenericGenerator(name = "user_id",strategy = "com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.UserIdGenerator")
//    @GeneratedValue(generator = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    private List<Role> role;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Seller seller;

    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Customer customer;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Address> address;

    @NotNull
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE
    )
    @Column(unique = true, updatable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isDelete;

    private String modifiedBy;

    private String createdBy;

    private String createdOn;

    private String modifiedOn;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isActive;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isExpired;

    @Column(columnDefinition = "Boolean default false")
    private Boolean isLocked;

    @Column(columnDefinition = "Integer default 0")
    private Integer invalidAttemptCount;

    @NotNull
    @Column(nullable = false)
    private String passwordUpdatedDate;

    @Override
    public String toString() {
        return "User{" +
                "Id=" + Id +
                '}';
    }
}
