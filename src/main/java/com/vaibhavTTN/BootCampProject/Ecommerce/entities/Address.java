package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String city;

    private String state;

    private String country;

    private String addressLine;

    private String zipCode;

    private String label;

    private Boolean isDeleted;

}
