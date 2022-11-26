package com.vaibhavTTN.BootCampProject.Ecommerce.cronJobs;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.enums.Roles;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.CustomException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.util.EmailSenderService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class Scheduler {

   @Autowired
   UserRepository userRepository;

   @Autowired
   EmailSenderService emailSenderService;

   @Scheduled(cron = "0 0 0 * * *")
   public void activateSeller() {
      List<User> user = userRepository.findAll();

      if(user.isEmpty()){
         return;
      }

      List<String> seller = user.stream().filter(e->{
         return e.getRole().equals(Roles.ROLE_SELLER.toString()) && !e.getIsActive();
      }).map(User::getEmail).toList();

            emailSenderService.sendListOfNotActivateSeller(seller);
   }


}