package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Role;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface roleRepository extends JpaRepository<Role,Long> {
    long count();
}
