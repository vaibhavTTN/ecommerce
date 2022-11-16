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

    @Column(columnDefinition = "boolean default false")
    private Boolean isDeleted;

}
