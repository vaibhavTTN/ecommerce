package com.vaibhavTTN.BootCampProject.Ecommerce.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private String gst;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyContact;

}
