package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  long count();

  Optional<User> findByEmail(String email);


}
