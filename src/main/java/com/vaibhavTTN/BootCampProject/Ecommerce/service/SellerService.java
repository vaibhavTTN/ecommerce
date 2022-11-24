package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerUpdateProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.SellerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.Utilities.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.CustomException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.properties.ApplicationProperties;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.AddressRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SellerService {

  private static final Logger logger = LoggerFactory.getLogger(SellerService.class);

  @Autowired
  UserRepository userRepository;

  @Autowired
  SellerRepository sellerRepository;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  EmailSenderService emailSenderService;

  @Autowired
  AddressRepository addressRepository;

  @Autowired
  ApplicationProperties applicationProperties;


  public SellerProfileDto getSellerProfile(Authentication authentication) {
    SellerProfileDto sellerProfileDto = new SellerProfileDto();
    String username = authentication.getName();

    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UserNotFoundException("invalid email"));

    Seller seller = sellerRepository.findByUser(user);
    sellerProfileDto.setId(user.getId());
    sellerProfileDto.setFirstName(user.getFirstName());
    sellerProfileDto.setLastName(user.getLastName());
    sellerProfileDto.setAddress(user.getAddress().get(0));
    sellerProfileDto.setGST(seller.getGst());
    sellerProfileDto.setCompanyName(seller.getCompanyName());
    sellerProfileDto.setCompanyContact(seller.getCompanyContact());
    sellerProfileDto.setIsActive(user.getIsActive());
    sellerProfileDto.setImage(
        applicationProperties.getUrl()+"/" + "seller" + File.separator + "image");

    return sellerProfileDto;
  }

  public String updateSellerPassword(Authentication authentication, UpdatePassword updatePassword) {
    String username = authentication.getName();

    User user = userRepository.findByEmail(username)
        .orElseThrow(() -> new UserNotFoundException("invalid email"));

    Seller seller = sellerRepository.findByUser(user);

    if (!bCryptPasswordEncoder.matches(updatePassword.getCurrentPassword(), user.getPassword())) {
      throw new PasswordMatchException("invalid Credential");
    }

    if (!updatePassword.getPassword().equals(updatePassword.getConfirmPassword())) {
      throw new PasswordMatchException("password does not matched with confirm password");
    }

    user.setPassword(bCryptPasswordEncoder.encode(updatePassword.getConfirmPassword()));
    userRepository.save(user);

    try {
      emailSenderService.sendCustomEmail(
          user,
          "Password Updated !",
          "Dear " + user.getFirstName() + " \n Your password is Updated."
      );
    } catch (Exception e) {
      logger.error(e.getMessage());
    }

    return "Your Password is Updated SuccessFully";
  }

  public String updateSellerAddress(Authentication authentication, Long id,
      Address requestAddress) {
    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new UserNotFoundException("invalid email"));

    Address address = user.getAddress().get(0);

    if (requestAddress.getAddressLine() != null) {
      address.setAddressLine(requestAddress.getAddressLine());
    }

    if (requestAddress.getCity() != null) {
      address.setCity(requestAddress.getCity());
    }

    if (requestAddress.getCity() != null) {
      address.setCity(requestAddress.getCity());
    }

    if (requestAddress.getLabel() != null) {
      address.setLabel(requestAddress.getLabel());
    }

    if (requestAddress.getState() != null) {
      address.setState(requestAddress.getState());
    }

    if (requestAddress.getZipCode() != null) {
      address.setZipCode(requestAddress.getZipCode());
    }

    addressRepository.save(address);
    return "Address is updated successfully";
  }

  public String updateSellerProfileImage(
      Authentication authentication,
      MultipartFile file
  ) throws IOException {

    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new CustomException("Email invalid"));

    String extension = "";
    String fileName = file.getOriginalFilename();

    int index = fileName.lastIndexOf('.');
    if (index > 0) {
      extension = fileName.substring(index + 1);
      System.out.println("File extension is " + extension);
    }

    if (extension.equals("")) {
      throw new CustomException("File must contain Extension");
    }

    switch (extension) {
      case "jpg":
        break;
      case "bmp":
        break;
      case "png":
        break;
      case "jpeg":
        break;
      default:
        throw new CustomException("File not supported");
    }

    fileName = applicationProperties.getImageUserPath() + File.separator + user.getId() + "."
        + extension;

    File f = new File(fileName);

    if (f.delete()) {
      logger.debug("file deleted");
    }

    File directory = new File(
        System.getProperty("user.dir") + applicationProperties.getImageUserPath());
    if (!directory.exists()) {
      try {
        directory.mkdir();
      } catch (SecurityException se) {
        return null;
      }
    }

    final String path = System.getProperty("user.dir") + fileName;
    try (InputStream inputStream = file.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path))) {
      byte[] buf = new byte[1024];
      int numRead = 0;
      while ((numRead = inputStream.read(buf)) >= 0) {
        fileOutputStream.write(buf, 0, numRead);
      }
    } catch (Exception e) {
      return null;
    }

    return "Seller Profile image is Updated";
  }

  public String sellerUpdateProfile(
      Authentication authentication,
      SellerUpdateProfileDto sellerProfileDto
  ) {
    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new CustomException("Email invalid"));

    Seller seller = sellerRepository.findByUser(user);

    if (sellerProfileDto.getCompanyContact() != null) {
      seller.setCompanyContact(sellerProfileDto.getCompanyContact());
    }

    if (sellerProfileDto.getCompanyName() != null) {
      seller.setCompanyContact(sellerProfileDto.getCompanyContact());
    }

    if (sellerProfileDto.getGst() != null) {
      seller.setGst(sellerProfileDto.getGst());
    }

    if (sellerProfileDto.getFirstName() != null) {
      user.setFirstName(sellerProfileDto.getFirstName());
    }

    if (sellerProfileDto.getMiddleName() != null) {
      user.setMiddleName(sellerProfileDto.getMiddleName());
    }

    if (sellerProfileDto.getLastName() != null) {
      user.setLastName(user.getLastName());
    }

    userRepository.save(user);
    sellerRepository.save(seller);

    return "Seller Profile is Updated";
  }

  public String getImage(Authentication authentication) {
    User user = userRepository.findByEmail(authentication.getName())
        .orElseThrow(() -> new CustomException("Email invalid"));

    File f = new File(System.getProperty("user.dir") + File.separator
        + applicationProperties.getImageUserPath());

    File[] matchingFiles = f.listFiles(new FilenameFilter() {
      public boolean accept(File dir, String name) {
        return name.startsWith(user.getId().toString());
      }
    });

    return matchingFiles[0].getName();
  }
}
