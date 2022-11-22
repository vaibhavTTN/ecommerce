package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerRepository extends JpaRepository<Seller,Long> {
    long count();

    Seller findByUser(User user);
}
