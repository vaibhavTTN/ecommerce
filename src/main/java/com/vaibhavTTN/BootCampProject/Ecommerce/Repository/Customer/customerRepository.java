package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface customerRepository extends JpaRepository<Customer,Long> {
    long count();
}
