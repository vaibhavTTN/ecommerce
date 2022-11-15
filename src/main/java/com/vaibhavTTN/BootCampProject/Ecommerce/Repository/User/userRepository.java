package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface userRepository extends JpaRepository<User,Long> {
    long count();

    Optional<User> findByEmail(String email);

}
