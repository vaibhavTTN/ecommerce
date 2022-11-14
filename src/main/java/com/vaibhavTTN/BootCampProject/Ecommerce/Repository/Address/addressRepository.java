package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Address;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface addressRepository extends JpaRepository<Address,Long> {
    long count();
}
