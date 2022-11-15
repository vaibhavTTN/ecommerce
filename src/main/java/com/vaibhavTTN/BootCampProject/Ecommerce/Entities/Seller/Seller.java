package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Seller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @Column(nullable = false)
    private String gst;

    @Column(nullable = false)
    private String companyName;

    @Column(nullable = false)
    private String companyContact;


}
