package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerUpdateProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.SellerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.SellerService;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/seller")
public class SellerController {

  @Autowired
  SellerService sellerService;

  @GetMapping("/image")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public ResponseEntity<byte[]> getImage(Authentication authentication) throws IOException {

    String userId = sellerService.getImage(authentication);

    var imgFile = new ClassPathResource("user_id/" + userId);
    byte[] bytes = StreamUtils.copyToByteArray(imgFile.getInputStream());

    return ResponseEntity
        .ok()
        .contentType(MediaType.IMAGE_JPEG)
        .body(bytes);
  }

  @GetMapping("/profile")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public SellerProfileDto getSellerProfile(Authentication authentication) {
    return sellerService.getSellerProfile(authentication);
  }

  @PatchMapping("/update-password")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public ResponseEntity<String> updateSellerPassword(Authentication authentication,
      @RequestBody UpdatePassword updatePassword) {
    String message = sellerService.updateSellerPassword(authentication, updatePassword);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-address/{id}")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public ResponseEntity<String> updateSellerAddress(Authentication authentication,
      @PathVariable("id") Integer id, @RequestBody Address requestAddress) {
    String message = sellerService.updateSellerAddress(authentication, Long.valueOf(id),
        requestAddress);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-image")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public ResponseEntity<String> updateSellerProfileImage(
      Authentication authentication,
      @RequestParam("image") MultipartFile file
  ) throws IOException {
    String message = sellerService.updateSellerProfileImage(authentication, file);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @PatchMapping("/update-profile")
  @PreAuthorize("hasAuthority('ROLE_SELLER')")
  public ResponseEntity<String> updateSellerProfile(
      Authentication authentication,
      @RequestBody SellerUpdateProfileDto sellerUpdateProfileDto
  ) throws IOException {
    String message = sellerService.sellerUpdateProfile(authentication, sellerUpdateProfileDto);
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @GetMapping("/dir")
  public String getDir() {
    return System.getProperty("user.dir");
  }

}
