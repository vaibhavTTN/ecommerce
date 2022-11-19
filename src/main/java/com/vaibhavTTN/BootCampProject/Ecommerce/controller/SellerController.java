package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SellerController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/sel")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public User getCus(){
        return userRepository.findById(3L).get();
    }
}
