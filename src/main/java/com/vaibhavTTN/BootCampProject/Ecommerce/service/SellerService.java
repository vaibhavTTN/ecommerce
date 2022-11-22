package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.SellerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.Utilities.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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

    public SellerProfileDto getSellerProfile(Authentication authentication){
        SellerProfileDto sellerProfileDto = new SellerProfileDto();
        String username =  authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException("invalid email"));

        Seller seller = sellerRepository.findByUser(user);
        sellerProfileDto.setId(user.getId());
        sellerProfileDto.setFirstName(user.getFirstName());
        sellerProfileDto.setLastName(user.getLastName());
        sellerProfileDto.setAddress(user.getAddress().get(0));
        sellerProfileDto.setGST(seller.getGst());
        sellerProfileDto.setCompanyName(seller.getCompanyName());
        sellerProfileDto.setCompanyContact(seller.getCompanyContact());
        sellerProfileDto.setIsActive(user.getIsActive());

        return sellerProfileDto;
    }

    public String updateSellerPassword(Authentication authentication,UpdatePassword updatePassword){
        String username  = authentication.getName();

        User user = userRepository.findByEmail(username)
                .orElseThrow(()->new UserNotFoundException("invalid email"));

        Seller seller = sellerRepository.findByUser(user);

        logger.debug(updatePassword.getConfirmPassword()+"heelo");
        if(!bCryptPasswordEncoder.matches(updatePassword.getCurrentPassword(),user.getPassword())){
            throw new PasswordMatchException("invalid Credential");
        }

        if(!updatePassword.getPassword().equals(updatePassword.getConfirmPassword())){
            throw new PasswordMatchException("password does not matched with confirm password");
        }

        user.setPassword(bCryptPasswordEncoder.encode(updatePassword.getConfirmPassword()));
        userRepository.save(user);

        try{
            emailSenderService.sendCustomEmail(
                    user,
                    "Password Updated !",
                    "Dear "+user.getFirstName()+" \n Your password is Updated."
            );
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return "Your Password is Updated SuccessFully";
    }

}
