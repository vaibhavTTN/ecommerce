package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.EmailDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.ResetPassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.AuthenticationService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private AuthenticationService authenticationService;

//    @PostMapping("/login")
//    public ResponseEntity<String> customerLogin(@RequestBody @Valid Login login){
//        Authentication authentication = authenticationManager
//                .authenticate(
//                        new UsernamePasswordAuthenticationToken(
//                            login.getUsername(), login.getPassword()
//                        )
//                );
//
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//
//        return new ResponseEntity<>("User Login successfully!.", HttpStatus.OK);
//    }


  @GetMapping("/do-logout")
  public ResponseEntity<String> logout(HttpServletRequest request) {
    authenticationService.logout(request);
    return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
  }

  @PostMapping("/forget-password")
  public ResponseEntity<String> forgetPassword(@RequestBody @Valid EmailDto emailDto) {
    authenticationService.forgetPassword(emailDto);
    return new ResponseEntity<>("Email has been sent for Forget Password", HttpStatus.OK);
  }

  @PutMapping("/reset-password")
  public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPassword resetPassword) {
    authenticationService.resetPassword(resetPassword);
    return new ResponseEntity<>("Password is changed", HttpStatus.CREATED);
  }
}
