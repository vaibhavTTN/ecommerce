package com.vaibhavTTN.BootCampProject.Ecommerce.Controller.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Service.Customer.customerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class customerController {

    @Autowired
    customerService customerService;



    @PostMapping("/customer")
    public void createCustomer(@RequestBody CustomerDetails customer){
        System.out.println(customer.toString());
        customerService.createCustomer(customer);
    }
}
