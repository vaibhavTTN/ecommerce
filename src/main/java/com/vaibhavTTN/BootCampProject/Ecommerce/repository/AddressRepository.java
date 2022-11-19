package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
    long count();
}
