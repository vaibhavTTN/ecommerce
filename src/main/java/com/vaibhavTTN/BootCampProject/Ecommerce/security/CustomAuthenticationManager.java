package com.vaibhavTTN.BootCampProject.Ecommerce.security;

import com.vaibhavTTN.BootCampProject.Ecommerce.util.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.CustomException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

  private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationManager.class);


  @Autowired
  UserRepository userRepository;

  @Autowired
  BCryptPasswordEncoder passwordEncoder;

  @Autowired
  EmailSenderService emailSenderService;


  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    logger.info("CustomAuthenticationManager::authenticate execution started.");
    logger.debug(
        "CustomAuthenticationManager::authenticate authenticating credentials, generating token");


    String username = authentication.getName();
    String password = authentication.getCredentials().toString();


    User user = userRepository.findByEmail(username).orElseThrow(
        () -> {
          logger.error("Exception occurred while Invalid credentials");
          throw new CustomException("Email not found :: " + username);
        }
    );


    if (Boolean.TRUE.equals(user.getIsDelete())) {
      throw new BadCredentialsException("Invalid Credentials");
    }

    if (Boolean.TRUE.equals(user.getIsLocked())) {
      throw new BadCredentialsException("Account is locked");
    }

    if (Boolean.FALSE.equals(user.getIsActive())) {
      throw new BadCredentialsException("Account is not Active");
    }

    if (!passwordEncoder.matches(password, user.getPassword())) {
      logger.debug("CustomAuthenticationManager::authenticate invalid attempt");
      int counter = user.getInvalidAttemptCount();

      if (counter < 2) {
        user.setInvalidAttemptCount(++counter);
        userRepository.save(user);
      } else {
        user.setIsLocked(true);
        user.setInvalidAttemptCount(0);
        userRepository.save(user);

        try {
          emailSenderService.sendCustomEmail(
              user,
              "Account is Locked",
              "Dear" + user.getFirstName() + ",\n Your Account is Locked ."
          );
        } catch (Exception e) {
          logger.error("Exception created while sending email by sendCustomEmail method \n {}",
              e.getMessage());
        }

        logger.debug("CustomAuthenticationManager::authenticate  account locked");
      }
      logger.error("Exception occurred while authenticating");
      throw new BadCredentialsException("Invalid Credentials");
    }

    List<GrantedAuthority> authorities = new ArrayList<>();
    authorities.add(new SimpleGrantedAuthority(user.getRole().getAuthority()));

    user.setInvalidAttemptCount(0); // reset counter on successful login
    userRepository.save(user);
    logger.debug("CustomAuthenticationManager::authenticate credentials authenticated ");
    logger.info("CustomAuthenticationManager::authenticate execution ended.");
    return new UsernamePasswordAuthenticationToken(username, password, authorities);
  }
}