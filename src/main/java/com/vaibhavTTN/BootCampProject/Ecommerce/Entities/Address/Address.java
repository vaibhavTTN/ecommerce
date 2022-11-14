package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Address {

//    @Id
//    @GenericGenerator(name = "address_id",strategy = "com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.AddressIdGenerator")
//    @GeneratedValue(generator = "address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String role;

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private Integer zipCode;

    private String label;

    private Boolean isDeleted;
}
