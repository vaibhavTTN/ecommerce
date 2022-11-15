package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Role;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface roleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByAuthority(String role);
    long count();
}
