package com.vaibhavTTN.BootCampProject.Ecommerce.util;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.ConfirmationToken;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.config.ApplicationProperties;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.TokenRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

  private static final Logger logger = LoggerFactory.getLogger(EmailSenderService.class);

  private final JavaMailSender javaMailSender;
  @Autowired
  TokenRepository tokenRepository;
  @Autowired
  ApplicationProperties applicationProperties;


  @Autowired
  public EmailSenderService(JavaMailSender javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Async
  public void sendEmailVerificationCustomer(User user) throws MailException {

    ConfirmationToken confirmationToken = new ConfirmationToken(user,
        LocalDateTime.now().plusHours(3));
    tokenRepository.save(confirmationToken);

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setSubject("Verify the Email");
    mail.setText("Click this Link For verify :: \n");
    mail.setText(
        applicationProperties.getUrl() +
            applicationProperties.getEmailVerifyUrl() + "/" +
            confirmationToken.getToken()
    );

    javaMailSender.send(mail);

    logger.debug("Email Sent for verification of Customer to :: {} ", user.getEmail());
  }

  @Async
  public void sendEmailVerificationSeller(User user) throws MailException {

    ConfirmationToken confirmationToken = new ConfirmationToken(user,
        LocalDateTime.now().plusWeeks(1));
    tokenRepository.save(confirmationToken);

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(applicationProperties.getAdminEmail());
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setSubject("Activate the Seller");
    mail.setText("Click this Link For Activation :: \n");
    mail.setText(
        applicationProperties.getUrl() +
            applicationProperties.getEmailVerifyUrl() + "/" +
            confirmationToken.getToken()
    );

    javaMailSender.send(mail);

    logger.debug("Email Sent for verification of Seller to :: {} ", user.getEmail());
  }

  @Async
  public void sendVerifiedEmail(User user) throws MailException {

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setSubject("Registration Completed!");
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setText("Congratulations! Your account is now verified.");

    javaMailSender.send(mail);

    logger.debug("Email Sent for verified to :: {} ", user.getEmail());
  }


  @Async
  public void sendEmailForgetPassword(User user) throws MailException {

    ConfirmationToken confirmationToken = new ConfirmationToken(user,
        LocalDateTime.now().plusMinutes(15));
    tokenRepository.save(confirmationToken);

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setSubject("Forget Password");
    mail.setText("Token For Forget Password");
    mail.setText(confirmationToken.getToken());
    javaMailSender.send(mail);

    logger.debug("Email Sent for forget password to :: {} ", user.getEmail());
  }

  @Async
  public void sendPasswordUpdated(User user) throws MailException {
    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setSubject("Password Updated!");
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setText("Dear User,\n your password was recently changed.");
    javaMailSender.send(mail);

    logger.debug("Email Sent for password updated to :: {} ", user.getEmail());
  }

  @Async
  public void sendCustomEmail(User user, String subject, String message) throws MailException {

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(user.getEmail());
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setSubject(subject);
    mail.setText(message);
    javaMailSender.send(mail);

    logger.debug("Email Sent for {} : to : {} ", subject, user.getEmail());
  }

  @Async
  public void sendListOfNotActivateSeller(List<String> seller) throws MailException {

    SimpleMailMessage mail = new SimpleMailMessage();
    mail.setTo(applicationProperties.getAdminEmail());
    mail.setSubject("Pending!! to Activate Seller!");
    mail.setFrom(applicationProperties.getSenderEmail());
    mail.setText("Dear Admin,\n Following user are left to verify.");

    javaMailSender.send(mail);

    logger.debug("Email Sent for not active list of seller");
  }


}
