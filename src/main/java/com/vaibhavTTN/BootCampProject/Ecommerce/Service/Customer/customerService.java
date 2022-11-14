package com.vaibhavTTN.BootCampProject.Ecommerce.Service.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO.CustomerDetails;
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


    public void createCustomer(CustomerDetails customerDetails) {
        User user = new User();
        List<User> userList = new ArrayList<>();
        userList.add(user);


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Role role = roleRepository.findById(2L).get();
        List<Role> roleList = new ArrayList<>();
        user.setRole(roleList);

        role.setUser(userList);

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

        System.out.println(user.toString());
        userRepository.save(user);
    }
}
