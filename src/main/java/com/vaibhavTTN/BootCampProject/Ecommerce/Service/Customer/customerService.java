package com.vaibhavTTN.BootCampProject.Ecommerce.Service.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.CustomerDetails;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Role.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Address.addressRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Customer.customerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Role.roleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class customerService {

    @Autowired
    customerRepository customerRepository;

    @Autowired
    userRepository userRepository;

    @Autowired
    roleRepository roleRepository;

    @Autowired
    addressRepository addressRepository;

    public void addAddress(Address address,Long Id){
        User user = customerRepository.findById(Id).get().getUser();

        List<Address> addressList = new ArrayList<>();
        user.setAddress(addressList);
        address.setUser(user);

        userRepository.save(user);
    }



}
