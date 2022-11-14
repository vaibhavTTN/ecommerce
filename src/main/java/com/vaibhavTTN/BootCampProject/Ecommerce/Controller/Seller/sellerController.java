package com.vaibhavTTN.BootCampProject.Ecommerce.Controller.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Service.Seller.sellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class sellerController {

    @Autowired
    sellerService sellerService;

    @PostMapping("/seller")
    public void createCustomer(@RequestBody SellerDetails seller){
        System.out.println(seller.toString());
        sellerService.createService(seller);
    }
}
