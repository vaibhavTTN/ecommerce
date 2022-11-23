package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerUpdateProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.SellerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.SellerService;
import io.micrometer.core.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

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
    public ResponseEntity<String> updateSellerPassword(Authentication authentication,@RequestBody UpdatePassword updatePassword){
        String message = sellerService.updateSellerPassword(authentication,updatePassword);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update-address/{id}")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> updateSellerAddress(@PathVariable("id") Integer id,@RequestBody Address requestAddress){
        String message = sellerService.updateSellerAddress(Long.valueOf(id),requestAddress);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @PatchMapping("/update-profile")
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    public ResponseEntity<String> updateSellerProfile(
            Authentication authentication,
            @RequestParam("image") MultipartFile file
//            @RequestParam SellerUpdateProfileDto sellerProfileDto
    ) throws IOException {
        String message = sellerService.updateSellerProfile(authentication,file);
//        String message = sellerService.updateSellerProfile(authentication,sellerProfileDto);
        return new ResponseEntity<>("Done", HttpStatus.ACCEPTED);
    }

    @GetMapping("/dir")
    public String getDir(){
        return System.getProperty("user.dir");
    }

}
