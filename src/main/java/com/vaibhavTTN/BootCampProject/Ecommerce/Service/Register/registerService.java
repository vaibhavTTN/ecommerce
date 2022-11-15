package com.vaibhavTTN.BootCampProject.Ecommerce.Service.Register;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.ExceptionHandling.EmailAlreadyExistsException;
import com.vaibhavTTN.BootCampProject.Ecommerce.ExceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Role.roleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Service.Email.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class registerService {

    @Autowired
    userRepository userRepository;
    @Autowired
    roleRepository roleRepository;

    @Autowired
    NotificationService notificationService;

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

        user.setRole(Set.of(role));

        user.setEmail(customerDetails.getEmail());
        user.setFirstName(customerDetails.getFirstName());
        user.setMiddleName(customerDetails.getMiddleName());
        user.setLastName(customerDetails.getLastName());
        user.setPassword(customerDetails.getPassword());
        user.setPasswordUpdatedDate(formatter.format(date));
        user.setCreatedBy(role.getAuthority());
        user.setModifiedBy(role.getAuthority());
        user.setModifiedOn(formatter.format(date));
        user.setCreatedOn(formatter.format(date));
        user.setInvalidAttemptCount(0);

        Customer customer = new Customer();
        customer.setContact(customerDetails.getContact());
        customer.setUser(user);

        user.setCustomer(customer);

        try {
            notificationService.sendNotificaitoin(user);
        }catch( Exception e ){
            System.out.println(e.getMessage());
        }


        System.out.println(user.toString());
        userRepository.save(user);
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

        Set<Role> roleSet = new HashSet<>();
        roleSet.add(role);

        user.setRole(roleSet);

        user.setEmail(sellerDetails.getEmail());
        user.setFirstName(sellerDetails.getFirstName());
        user.setMiddleName(sellerDetails.getMiddleName());
        user.setLastName(sellerDetails.getLastName());
        user.setPassword(sellerDetails.getPassword());
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
        address.setUser(user);

        user.addAddress(address);

        user.setSeller(seller);

        try {
            notificationService.sendNotificaitoin(user);
        }catch( Exception e ){
            System.out.println(e.getMessage());
        }

        System.out.println(user.toString());
        userRepository.save(user);
    }
}
