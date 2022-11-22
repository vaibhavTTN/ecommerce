package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.AllCustomerDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.AllSellerDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.Utilities.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.controller.AdminController;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Seller;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.enums.Roles;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserIsAlreadyActive;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.CustomerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.RoleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.SellerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    EmailSenderService emailSender;

    public Pageable pagination(Integer page,Integer size,String[] sort){

        page = (page==null)?0:page;
        size = (size==null)?10:size;
        sort[0] = (sort[0]==null)?"id":sort[0];
        sort[1] = (sort[1]==null)?"asc":sort[1];

        Sort sortBy;
        if(sort[1].equals("asc")){
            sortBy = Sort.by(sort[0]).ascending();
        }else {
            sortBy = Sort.by(sort[0]).descending();
        }

        return PageRequest.of(page,size,sortBy);
    }

    public Page<AllCustomerDto> AllCustomerList(Integer page, Integer size, String[] sort, String email){

        logger.debug("Page No {} , Page Size {}, Sort {}, Email {}",page,size,sort,email);

        Pageable pageable = pagination(page,size,sort);

        if(email!=null){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new UserNotFoundException("Invalid Email"));

            final Customer customer = customerRepository.findByUser(user);

            List<AllCustomerDto> customerDtos = List.of(user)
                .stream()
                .filter(e->e.getRole().getAuthority().equals(Roles.ROLE_CUSTOMER.toString()))
                .map((e->{
                    AllCustomerDto allCustomerDto = new AllCustomerDto();
                    String fullName = "";
                    if(e.getMiddleName()==null){
                        fullName += e.getFirstName() + " " + e.getLastName();
                    }else{
                        fullName += e.getFirstName()+" ";
                        fullName += e.getMiddleName() + " ";
                        fullName += e.getLastName();
                    }
                    allCustomerDto.setId(e.getId());
                    allCustomerDto.setEmail(e.getEmail());
                    allCustomerDto.setFullName(fullName);
                    allCustomerDto.setIsActive(e.getIsActive());
                    allCustomerDto.setContact(customer.getContact());

                    Link link;
                    if(customer.getUser().getIsActive()){
                        link = linkTo(methodOn(AdminController.class)
                                .deActivateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }else {
                        link = linkTo(methodOn(AdminController.class)
                                .activateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }
                    allCustomerDto.add(link);

                    return allCustomerDto;
                })).toList();

            return new PageImpl(customerDtos, pageable, customerDtos.size());
        }


        Page<Customer> customers = customerRepository.findAll(pageable);

         List<AllCustomerDto> customerDtos = customers.stream()
                .map((e)->{
                    AllCustomerDto allCustomerDto = new AllCustomerDto();
                    String fullName = "";
                    if(e.getUser().getMiddleName()==null){
                        fullName += e.getUser().getFirstName() + " " + e.getUser().getLastName();
                    }else{
                        fullName += e.getUser().getFirstName()+" ";
                        fullName += e.getUser().getMiddleName() + " ";
                        fullName += e.getUser().getLastName();
                    }
                    allCustomerDto.setId(e.getId());
                    allCustomerDto.setEmail(e.getUser().getEmail());
                    allCustomerDto.setFullName(fullName);
                    allCustomerDto.setIsActive(e.getUser().getIsActive());
                    allCustomerDto.setContact(e.getContact());

                    Link link;
                    if(e.getUser().getIsActive()){
                        link = linkTo(methodOn(AdminController.class)
                                .deActivateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }else {
                        link = linkTo(methodOn(AdminController.class)
                                .activateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }

                    allCustomerDto.add(link);

                    return allCustomerDto;
                }).toList();

       return new PageImpl(customerDtos, pageable, customerDtos.size());
    }

    public Page<AllSellerDto> AllSellerList(Integer page, Integer size, String[] sort, String email){

        logger.debug("Page No {} , Page Size {}, SortProperty {}, sortDirection {}, Email {}",page,size,sort,email);

        Pageable pageable = pagination(page,size,sort);

        if(email!=null){
            User user = userRepository.findByEmail(email)
                    .orElseThrow(()->new UserNotFoundException("Invalid Email"));

            final Seller seller = sellerRepository.findByUser(user);

            List<AllSellerDto> sellerDtos = userRepository.findByEmail(email)
                    .stream()
                    .filter(e->e.getRole().getAuthority().equals(Roles.ROLE_SELLER.toString()))
                    .map((e->{
                        AllSellerDto allSellerDto = new AllSellerDto();
                        String fullName = "";
                        if(e.getMiddleName()==null){
                            fullName += e.getFirstName() + " " + e.getLastName();
                        }else{
                            fullName += e.getFirstName()+" ";
                            fullName += e.getMiddleName() + " ";
                            fullName += e.getLastName();
                        }
                        allSellerDto.setId(e.getId());
                        allSellerDto.setEmail(e.getEmail());
                        allSellerDto.setFullName(fullName);
                        allSellerDto.setIsActive(e.getIsActive());
                        allSellerDto.setCompanyName(seller.getCompanyName());
                        allSellerDto.setCompanyContact(seller.getCompanyContact());
                        allSellerDto.setAddress(seller.getUser().getAddress().get(0));

                        Link link;
                        if(seller.getUser().getIsActive()){
                            link = linkTo(methodOn(AdminController.class)
                                    .deActivateCustomer(e.getId().intValue()))
                                    .withRel("id");
                        }else {
                            link = linkTo(methodOn(AdminController.class)
                                    .activateCustomer(e.getId().intValue()))
                                    .withRel("id");
                        }

                        allSellerDto.add(link);

                        return allSellerDto;
                    })).toList();

            return new PageImpl<AllSellerDto>(sellerDtos, pageable, sellerDtos.size());
        }


        Page<Seller> sellers = sellerRepository.findAll(pageable);

        List<AllSellerDto> sellerDtos = sellers.stream()
                .map((e)->{
                    AllSellerDto allSellerDto = new AllSellerDto();
                    String fullName = "";
                    if(e.getUser().getMiddleName()==null){
                        fullName += e.getUser().getFirstName() + " " + e.getUser().getLastName();
                    }else{
                        fullName += e.getUser().getFirstName()+" ";
                        fullName += e.getUser().getMiddleName() + " ";
                        fullName += e.getUser().getLastName();
                    }
                    allSellerDto.setId(e.getId());
                    allSellerDto.setEmail(e.getUser().getEmail());
                    allSellerDto.setFullName(fullName);
                    allSellerDto.setIsActive(e.getUser().getIsActive());
                    allSellerDto.setCompanyName(e.getCompanyName());
                    allSellerDto.setCompanyContact(e.getCompanyContact());
                    allSellerDto.setAddress(e.getUser().getAddress().get(0));

                    Link link;
                    if(e.getUser().getIsActive()){
                        link = linkTo(methodOn(AdminController.class)
                                .deActivateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }else {
                        link = linkTo(methodOn(AdminController.class)
                                .activateCustomer(e.getId().intValue()))
                                .withRel("id");
                    }
                    allSellerDto.add(link);
                    return allSellerDto;
                }).toList();

        return new PageImpl<AllSellerDto>(sellerDtos, pageable, sellerDtos.size());
    }

    public void CustomerActivate(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        if (customer.getUser().getIsActive()){
            throw new UserIsAlreadyActive("Customer is Already Active");
        }

        User user = customer.getUser();

        user.setIsActive(true);
        userRepository.save(user);

        try {
            emailSender.sendCustomEmail(
                    user,
                    "Activated !!",
                    "Dear Customer, \n Your account has been activated ."
            );
        }catch (Exception e){
            logger.error("Email custom email method :: {}",e.getMessage());
        }


    }


    public void SellerActivate(Long id){
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        if (seller.getUser().getIsActive()){
            throw new UserIsAlreadyActive("Seller is Already Active");
        }

        User user = seller.getUser();

        user.setIsActive(true);
        userRepository.save(user);

        try {
            emailSender.sendCustomEmail(
                    user,
                    "Activated !!",
                    "Dear Seller, \n Your account has been activated ."
            );
        }catch (Exception e){
            logger.error("Email custom email method :: {}",e.getMessage());
        }
    }

    public void CustomerDeActivate(Long id){
        Customer customer = customerRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        if (!customer.getUser().getIsActive()){
            throw new UserIsAlreadyActive("Seller is Already De-Activated");
        }

        User user = customer.getUser();

        user.setIsActive(false);
        userRepository.save(user);

        try {
            emailSender.sendCustomEmail(
                    user,
                    "De-Activated !!",
                    "Dear Customer, \n Your account has been de-activated ."
            );
        }catch (Exception e){
            logger.error("Email custom email method :: {}",e.getMessage());
        }
    }


    public void SellerDeActivate(Long id){
        Seller seller = sellerRepository.findById(id)
                .orElseThrow(()->new UserNotFoundException("User not found"));

        if (!seller.getUser().getIsActive()){
            throw new UserIsAlreadyActive("Seller is Already De-Activated");
        }

        User user = seller.getUser();

        user.setIsActive(false);
        userRepository.save(user);

        try {
            emailSender.sendCustomEmail(
                    user,
                    "De-Activated !!",
                    "Dear Seller, \n Your account has been de-activated ."
            );
        }catch (Exception e){
            logger.error("Email custom email method :: {}",e.getMessage());
        }
    }



}
