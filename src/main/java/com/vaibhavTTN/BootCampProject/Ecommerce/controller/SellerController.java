package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.SellerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seller")
public class SellerController {

    @Autowired
    SellerService sellerService;

    @GetMapping("/profile")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public SellerProfileDto getSellerProfile(Authentication authentication){
        return sellerService.getSellerProfile(authentication);
    }

    @PatchMapping("/update-password")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> updatePasswordSeller(Authentication authentication,@RequestBody UpdatePassword updatePassword){
        String message = sellerService.updateSellerPassword(authentication,updatePassword);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }


}
