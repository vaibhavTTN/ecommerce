package com.vaibhavTTN.BootCampProject.Ecommerce.controller;


import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.EmailDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @PutMapping("/verify/{token}")
    public ResponseEntity<String> verify(@PathVariable("token") String token){
        registerService.verify(token);
        return new ResponseEntity<>("Verification Email is Verified",HttpStatus.OK);
    }

    @PostMapping("/re-verify")
    public ResponseEntity<String> re_send(@RequestBody EmailDto emailDto){
        registerService.resendVerify(emailDto.getEmail());
        return new ResponseEntity<>("Verification Email is re-send",HttpStatus.OK);
    }

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
    public ResponseEntity<User> createSeller(@RequestBody @Valid SellerDetails seller){
        System.out.println(seller.toString());
        registerService.createSeller(seller);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path(null)
                .build()
                .toUri();
        return ResponseEntity.created(location).build();
    }


}
