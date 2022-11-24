package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {


  long count();

  Optional<Role> findByAuthority(String authority);
}
