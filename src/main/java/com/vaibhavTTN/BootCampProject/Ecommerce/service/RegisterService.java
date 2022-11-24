package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Utilities.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.ConfirmationToken;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.enums.Roles;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.EmailAlreadyExistsException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.TokenExpired;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserIsAlreadyActive;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.CustomerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.RoleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.TokenRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

  private static final Logger logger = LoggerFactory.getLogger(RegisterService.class);

  @Autowired
  UserRepository userRepository;
  @Autowired
  RoleRepository roleRepository;

  @Autowired
  EmailSenderService senderService;

  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  TokenRepository tokenRepository;

  @Autowired
  CustomerRepository customerRepository;

  @Autowired
  SellerRepository sellerRepository;

  public boolean checkPassword(String password, String confirmPassword) {
    return !password.equals(confirmPassword);
  }

  public boolean emailAlreadyExitCheck(String email) {
    return userRepository.findByEmail(email).isPresent();
  }

  public void sendEmail(User user) {
    if (user.getRole().getAuthority().equals(Roles.ROLE_CUSTOMER.toString())) {
      try {
        senderService.sendEmailVerificationCustomer(user);
      } catch (Exception e) {
        logger.debug("Error in sending Email by sendEmailVerificationCustomer method \n {} ",
            e.getMessage());
      }
    } else {
      try {
        senderService.sendEmailVerificationSeller(user);
      } catch (Exception e) {
        logger.error("Error in sending Email by sendEmailVerificationSeller method \n {} ",
            e.getMessage());
      }
    }
  }

  public void verify(String token) {
    ConfirmationToken confirmationToken = tokenRepository
        .findByToken(token)
        .orElseThrow(() -> new TokenExpired("This token is invalid"));

    if (confirmationToken.isExpired()) {
      throw new TokenExpired("This token is invalid");
    }

    User user = confirmationToken.getUser();

    user.setIsActive(true);

    userRepository.save(user);

    try {
      senderService.sendVerifiedEmail(user);
    } catch (Exception e) {
      logger.error("Error in sending Email by sendVerifiedEmail method \n {} ", e.getMessage());
    }
    tokenRepository.delete(confirmationToken);
  }

  public void resendVerify(String email) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new UserNotFoundException("Email is not present"));

    if (Boolean.TRUE.equals(user.getIsActive())) {
      throw new UserIsAlreadyActive("User is already active");
    }

    ConfirmationToken confirmationToken = tokenRepository
        .findByUser(user).orElseThrow(() -> new UserNotFoundException("User is not present"));

    tokenRepository.delete(confirmationToken);

    sendEmail(user);
  }

  public void createCustomer(CustomerDetails customerDetails) {

    if (emailAlreadyExitCheck(customerDetails.getEmail())) {
      throw new EmailAlreadyExistsException("Email Already exits ::" + customerDetails.getEmail());
    }

    if (checkPassword(
        customerDetails.getPassword(),
        customerDetails.getConfirmPassword()
    )) {
      throw new PasswordMatchException("password and confirm password does not match");
    }

    User user = new User();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    Role role = roleRepository.findByAuthority("ROLE_CUSTOMER").orElseThrow();

    user.setRole(role);

    user.setEmail(customerDetails.getEmail());
    user.setFirstName(customerDetails.getFirstName());
    user.setMiddleName(customerDetails.getMiddleName());
    user.setLastName(customerDetails.getLastName());
    user.setPassword(bCryptPasswordEncoder.encode(customerDetails.getPassword()));
    user.setPasswordUpdatedDate(LocalDateTime.now());
    user.setCreatedBy(role.getAuthority());
    user.setModifiedBy(role.getAuthority());
    user.setModifiedAt(LocalDateTime.now());
    user.setCreatedAt(LocalDateTime.now());
    user.setInvalidAttemptCount(0);
    user.setIsLocked(false);
    user.setIsActive(false);

    Customer customer = new Customer();
    customer.setContact(customerDetails.getContact());
    customer.setUser(user);

    customerRepository.save(customer);

    try {
      senderService.sendEmailVerificationCustomer(user);
    } catch (Exception e) {
      logger.error("Error in sending Email by sendEmailVerificationCustomer method \n {} ",
          e.getMessage());
    }

  }

  public void createSeller(SellerDetails sellerDetails) {

    if (emailAlreadyExitCheck(sellerDetails.getEmail())) {
      throw new EmailAlreadyExistsException("Email Already exits ::" + sellerDetails.getEmail());
    }

    if (checkPassword(
        sellerDetails.getPassword(),
        sellerDetails.getConfirmPassword()
    )) {
      throw new PasswordMatchException("password and confirm password does not match");
    }

    User user = new User();

    SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

    Role role = roleRepository.findByAuthority("ROLE_SELLER").orElseThrow();

    user.setRole(role);

    user.setEmail(sellerDetails.getEmail());
    user.setFirstName(sellerDetails.getFirstName());
    user.setMiddleName(sellerDetails.getMiddleName());
    user.setLastName(sellerDetails.getLastName());
    user.setPassword(bCryptPasswordEncoder.encode(sellerDetails.getPassword()));
    user.setPasswordUpdatedDate(LocalDateTime.now());
    user.setCreatedBy(role.getAuthority());
    user.setModifiedBy(role.getAuthority());
    user.setModifiedAt(LocalDateTime.now());
    user.setCreatedAt(LocalDateTime.now());
    user.setInvalidAttemptCount(0);
    user.setIsLocked(false);
    user.setIsActive(false);

    Seller seller = new Seller();
    seller.setGst(sellerDetails.getGst());
    seller.setCompanyContact(sellerDetails.getCompanyContact());
    seller.setCompanyName(sellerDetails.getCompanyName());
    seller.setUser(user);

    Address address = new Address();
    address.setAddressLine(sellerDetails.getAddressLine());
    address.setCity(sellerDetails.getCity());
    address.setCountry(sellerDetails.getCountry());
    address.setLabel(sellerDetails.getLabel());
    address.setState(sellerDetails.getState());
    address.setZipCode(sellerDetails.getZipCode());

    user.setAddress(List.of(address));

    seller.setUser(user);

    sellerRepository.save(seller);

    try {
      senderService.sendEmailVerificationSeller(user);
    } catch (Exception e) {
      logger.error("Error in sending Email by sendEmailVerificationSeller method \n {} ",
          e.getMessage());
    }

  }
}
