package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO.EmailDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO.ResetPassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.util.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.ConfirmationToken;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.TokenExpired;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserIsNotActive;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.TokenRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService {

  private static final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

  @Autowired
  UserRepository userRepository;

  @Autowired
  TokenRepository tokenRepository;

  @Autowired
  EmailSenderService senderService;
  @Autowired
  BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private TokenStore tokenStore;

  public boolean checkPassword(String password, String confirmPassword) {
    return !password.equals(confirmPassword);
  }

  public void forgetPassword(EmailDto email) {
    User user = userRepository.findByEmail(email.getEmail())
        .orElseThrow(() -> new UserNotFoundException("Email invalid"));

    Long confirmationTokenCount = tokenRepository.count();
    if (confirmationTokenCount > 0) {
      ConfirmationToken confirmationToken = tokenRepository
          .findByUser(user).get();
      if (confirmationToken != null) {
        tokenRepository.delete(confirmationToken);
      }
    }

    if (Boolean.FALSE.equals(user.getIsActive())) {
      throw new UserIsNotActive("User is not Active");
    }

    if (Boolean.TRUE.equals(user.getIsLocked())) {
      throw new UserIsNotActive("User Account is Locked");
    }

    try {
      senderService.sendEmailForgetPassword(user);
    } catch (Exception e) {
      logger.error("Error in sending Email by sendEmailForgetPassword method {} ", e.getMessage());
    }
  }

  public void resetPassword(ResetPassword resetPassword) {
    User user = userRepository.findByEmail(resetPassword.getEmail())
        .orElseThrow(() -> new UserNotFoundException("Email invalid"));

    ConfirmationToken confirmationToken = tokenRepository
        .findByToken(resetPassword.getToken())
        .orElseThrow(() -> new TokenExpired("Token is invalid"));

    if (confirmationToken.isExpired()) {
      throw new TokenExpired("Token is invalid");
    }

    if (Boolean.FALSE.equals(user.getIsActive())) {
      throw new UserIsNotActive("User is not Active");
    }

    if (checkPassword(resetPassword.getPassword(), resetPassword.getConfirmPassword())) {
      throw new PasswordMatchException("password and confirm password does not match");
    }

    user.setPassword(bCryptPasswordEncoder.encode(resetPassword.getPassword()));
    user.setModifiedAt(LocalDateTime.now());

    userRepository.save(user);

    tokenRepository.delete(confirmationToken);

    try {
      senderService.sendPasswordUpdated(user);
    } catch (Exception e) {
      logger.error("Error in sending Email by sendPasswordUpdated method {} ", e.getMessage());
    }
  }


  public void logout(HttpServletRequest request) {
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null) {
      String tokenValue = authHeader.replace("Bearer", "").trim();
      OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
      tokenStore.removeAccessToken(accessToken);
    }
  }
}
