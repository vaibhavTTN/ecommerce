package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

  long count();

  List<Customer> findAll();

  Optional<Customer> findByUser(User user);
}
