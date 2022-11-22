package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer,Long> {
    long count();
    List<Customer> findAll();

    Customer findByUser(User user);
}
