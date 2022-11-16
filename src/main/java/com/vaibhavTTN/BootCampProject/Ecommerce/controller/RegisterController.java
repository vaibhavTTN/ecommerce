package com.vaibhavTTN.BootCampProject.Ecommerce.controller;


import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.registerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class RegisterController {

    @Autowired
    registerService registerService;

    @PostMapping("/customer")
    public ResponseEntity<User> createCustomer(@RequestBody @Valid CustomerDetails customer){
        System.out.println(customer.toString());
        registerService.createCustomer(customer);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(null)
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/seller")
    public ResponseEntity<User> createCustomer(@RequestBody @Valid SellerDetails seller){
        System.out.println(seller.toString());
        registerService.createService(seller);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(null)
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
