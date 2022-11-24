package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerUpdateProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.CustomerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.CustomerService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  CustomerService customerService;

  @GetMapping("/image")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<byte[]> getImage(Authentication authentication) throws IOException {

    String userId = customerService.getImage(authentication);

    var imgFile = new ClassPathResource("user_id/" + userId);
    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

    return ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(bytes);
  }

  @GetMapping("/profile")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public CustomerProfileDto getSellerProfile(Authentication authentication) {
    return customerService.getCustomerProfile(authentication);
  }

  @PatchMapping("/update-password")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> updateSellerPassword(
      Authentication authentication,
      @RequestBody UpdatePassword updatePassword
  ) {
    String message = customerService.updateCustomerPassword(authentication, updatePassword);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-image")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> updateSellerProfileImage(
      Authentication authentication,
      @RequestParam("image") MultipartFile file
  ) throws IOException {
    String message = customerService.updateCustomerProfileImage(authentication, file);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-profile")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> updateSellerProfile(
      Authentication authentication,
      @RequestBody CustomerUpdateProfileDto customerUpdateProfileDto
  ) throws IOException {
    String message = customerService.customerUpdateProfile(authentication,
        customerUpdateProfileDto);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-address/{id}")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> updateSellerAddress(
      Authentication authentication,
      @PathVariable("id") Integer id,
      @RequestBody Address requestAddress
  ) {
    String message = customerService.updateCustomerAddress(
        authentication,
        Long.valueOf(id),
        requestAddress
    );
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PostMapping("/address")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> addCustomerAddress(
      Authentication authentication,
      @RequestBody Address address
  ) {
    String message = customerService.addCustomerAddress(authentication, address);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @DeleteMapping("/address/{id}")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public ResponseEntity<String> deleteCustomerAddress(
      Authentication authentication,
      @PathVariable("id") Integer id
  ) {
    String message = customerService.deleteCustomerProfileAddress(
        authentication,
        Long.valueOf(id)
    );
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @GetMapping("/cus")
  @PreAuthorize("hasAuthority('ROLE_CUSTOMER')")
  public User getCus() {
    return userRepository.findById(2L).get();
  }
}
