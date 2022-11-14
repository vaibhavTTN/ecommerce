package com.vaibhavTTN.BootCampProject.Ecommerce.Service.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.SellerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Role.roleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Seller.sellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class sellerService {

    @Autowired
    roleRepository roleRepository;

    @Autowired
    sellerRepository sellerRepository;

    @Autowired
    userRepository userRepository;

    public void createService(SellerDetails sellerDetails){
        User user = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findById(3L).get();
        List<Role> roleList = new ArrayList<>();
        user.setRole(roleList);

        role.setUser(userList);


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
        seller.setUser(user);
        seller.setGst(sellerDetails.getGst());
        seller.setCompanyContact(sellerDetails.getCompanyContact());
        seller.setCompanyName(sellerDetails.getCompanyName());
        seller.setUser(user);

        user.setSeller(seller);

        System.out.println(user.toString());
        userRepository.save(user);
    }
}
