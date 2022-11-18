package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.EmailAlreadyExistsException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.CustomerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.roleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class registerService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    roleRepository roleRepository;

    @Autowired
    NotificationService notificationService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    SellerRepository sellerRepository;

    public boolean matchPassword(String password,String confirmPassword){
        return !password.equals(confirmPassword);
    }

    public boolean emailAlreadyExitCheck(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public void createCustomer(CustomerDetails customerDetails) {

        if(emailAlreadyExitCheck(customerDetails.getEmail())){
            throw new EmailAlreadyExistsException("Email Already exits ::"+ customerDetails.getEmail());
        }

        if(matchPassword(
                customerDetails.getPassword(),
                customerDetails.getConfirmPassword()
        )){
            throw new PasswordMatchException("password and confirm password does not match");
        }

        User user = new User();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findByAuthority("ROLE_CUSTOMER").orElseThrow();
        System.out.println(role.toString());

        user.setRole(role);

        user.setEmail(customerDetails.getEmail());
        user.setFirstName(customerDetails.getFirstName());
        user.setMiddleName(customerDetails.getMiddleName());
        user.setLastName(customerDetails.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(customerDetails.getPassword()));
        user.setPasswordUpdatedDate(formatter.format(date));
        user.setCreatedBy(role.getAuthority());
        user.setModifiedBy(role.getAuthority());
        user.setModifiedOn(formatter.format(date));
        user.setCreatedOn(formatter.format(date));
        user.setInvalidAttemptCount(0);

        Customer customer = new Customer();
        customer.setContact(customerDetails.getContact());
        customer.setUser(user);

        customerRepository.save(customer);

//        try {
//            notificationService.sendNotificaitoin(user);
//        }catch( Exception e ){
//            System.out.println(e.getMessage());
//        }


        System.out.println(user.toString());
//        userRepository.save(user);
    }

    public void createService(SellerDetails sellerDetails){

        if(emailAlreadyExitCheck(sellerDetails.getEmail())){
            throw new EmailAlreadyExistsException("Email Already exits ::"+ sellerDetails.getEmail());
        }

        if(matchPassword(
                sellerDetails.getPassword(),
                sellerDetails.getConfirmPassword()
        )){
            throw new PasswordMatchException("password and confirm password does not match");
        }

        User user = new User();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findByAuthority("ROLE_SELLER").orElseThrow();
        System.out.println(role.toString());

        user.setRole(role);

        user.setEmail(sellerDetails.getEmail());
        user.setFirstName(sellerDetails.getFirstName());
        user.setMiddleName(sellerDetails.getMiddleName());
        user.setLastName(sellerDetails.getLastName());
        user.setPassword(bCryptPasswordEncoder.encode(sellerDetails.getPassword()));
        user.setPasswordUpdatedDate(formatter.format(date));
        user.setCreatedBy(role.getAuthority());
        user.setModifiedBy(role.getAuthority());
        user.setModifiedOn(formatter.format(date));
        user.setCreatedOn(formatter.format(date));
        user.setInvalidAttemptCount(0);

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

//        try {
//            notificationService.sendNotificaitoin(user);
//        }catch( Exception e ){
//            System.out.println(e.getMessage());
//        }

        System.out.println(user.toString());
        sellerRepository.save(seller);
    }
}
