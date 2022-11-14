package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface userRepository extends JpaRepository<User,Long> {
    long count();
}
