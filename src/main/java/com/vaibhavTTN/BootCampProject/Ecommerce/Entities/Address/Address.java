package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

//    @Id
//    @GenericGenerator(name = "address_id",strategy = "com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.AddressIdGenerator")
//    @GeneratedValue(generator = "address_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String addressLine;

    @Column(nullable = false)
    private String zipCode;

    @Column(nullable = false)
    private String label;

    @Column(nullable = false,columnDefinition = "boolean default false")
    private Boolean isDeleted;

}
