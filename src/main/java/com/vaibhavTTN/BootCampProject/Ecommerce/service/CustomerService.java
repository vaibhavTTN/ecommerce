package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    public String addCustomerAddress(Authentication authentication, Address address) {
        
        return "Customer Address is Added";
    }
}
