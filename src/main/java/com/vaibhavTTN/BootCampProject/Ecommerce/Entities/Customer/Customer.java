package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Data
public class Customer {
//    @Id
//    @GenericGenerator(name = "customer_id",strategy = "com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.CustomerIdGenerator")
//    @GeneratedValue(generator = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    private String contact;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}
