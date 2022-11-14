package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Seller {

//    @Id
//    @GenericGenerator(name = "seller_id",strategy = "com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.SellerIdGenerator")
//    @GeneratedValue(generator = "seller_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String gst;

    private String companyName;

    private String companyContact;

    @Override
    public String toString() {
        return "Seller{" +
                "id=" + id +
                '}';
    }
}
