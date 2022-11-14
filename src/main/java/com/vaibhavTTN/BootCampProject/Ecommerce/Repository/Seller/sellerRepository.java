package com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface sellerRepository extends JpaRepository<Seller,Long> {
    long count();
}
