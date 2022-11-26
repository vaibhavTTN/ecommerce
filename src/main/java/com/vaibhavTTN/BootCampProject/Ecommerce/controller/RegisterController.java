package com.vaibhavTTN.BootCampProject.Ecommerce.controller;


import com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO.EmailDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.RegisterService;
import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/register")
public class RegisterController {

  @Autowired
  RegisterService registerService;

  @Autowired
  UserRepository userRepository;

  @PutMapping("/verify/{token}")
  public ResponseEntity<String> verify(@PathVariable("token") String token) {
    registerService.verify(token);
    return new ResponseEntity<>("Verification Email is Verified", HttpStatus.OK);
  }

  @PostMapping("/re-verify")
  public ResponseEntity<String> re_send(@RequestBody EmailDto emailDto) {
    registerService.resendVerify(emailDto.getEmail());
    return new ResponseEntity<>("Verification Email is re-send", HttpStatus.OK);
  }

  @PostMapping("/customer")
  public ResponseEntity<User> createCustomer(@RequestBody @Valid CustomerDetails customer) {
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
  public ResponseEntity<User> createSeller(@RequestBody @Valid SellerDetails seller) {
    System.out.println(seller.toString());
    registerService.createSeller(seller);
    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path(null)
        .build()
        .toUri();
    return ResponseEntity.created(location).build();
  }

//    @GetMapping("/role")
//    public int role(){
//        return .findRoleId(Roles.ROLE_CUSTOMER.toString());
//    }


}
