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


}
