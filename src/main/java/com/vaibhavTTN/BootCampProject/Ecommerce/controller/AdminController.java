package com.vaibhavTTN.BootCampProject.Ecommerce.controller;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.AllCustomerDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.AllSellerDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    AdminService adminService;


    @Autowired
    private PagedResourcesAssembler<AllCustomerDto> allCustomerDtoPagedResourcesAssembler;

    @Autowired
    private PagedResourcesAssembler<AllSellerDto> allSellerDtoPagedResourcesAssembler;


    @GetMapping("/customers")
    public PagedModel<EntityModel<AllCustomerDto>> customers(
            @Param("page") Integer page,
            @Param("size") Integer size,
            @Param("sort") String[] sort,
            @Param("email") String email)
    {
       Page<AllCustomerDto> customerPage = adminService.AllCustomerList(page,size,sort,email);
       PagedModel<EntityModel<AllCustomerDto>> pagedModel = allCustomerDtoPagedResourcesAssembler.toModel(customerPage);
       return pagedModel;
    }

    @GetMapping("/sellers")
    public PagedModel<EntityModel<AllSellerDto>> sellers(
            @Param("page") Integer page,
            @Param("size") Integer size,
            @Param("sort") String[] sort,
            @Param("email") String email)
    {
        Page<AllSellerDto> sellerPage = adminService.AllSellerList(page,size,sort,email);
        PagedModel<EntityModel<AllSellerDto>> pagedModel = allSellerDtoPagedResourcesAssembler.toModel(sellerPage);
        return pagedModel;
    }

    @PatchMapping("/customer/activate/{id}")
    public ResponseEntity<String> activateCustomer(@PathVariable("id") Integer id){
        adminService.CustomerActivate(Long.valueOf(id));
        return new ResponseEntity<>("Customer is activated", HttpStatus.ACCEPTED);
    }


    @PatchMapping("/seller/activate/{id}")
    public ResponseEntity<String> activateSeller(@PathVariable("id") Integer id){
        adminService.SellerActivate(Long.valueOf(id));
        return new ResponseEntity<>("Seller is activated", HttpStatus.ACCEPTED);
    }


    @PatchMapping("/customer/de-activate/{id}")
    public ResponseEntity<String> deActivateCustomer(@PathVariable("id") Integer id){
        adminService.CustomerDeActivate(Long.valueOf(id));
        return new ResponseEntity<>("Customer is de-activated", HttpStatus.ACCEPTED);
    }


    @PatchMapping("/seller/de-activate/{id}")
    public ResponseEntity<String> deActivateSeller(@PathVariable("id") Integer id){
        adminService.SellerDeActivate(Long.valueOf(id));
        return new ResponseEntity<>("Seller is de-activated", HttpStatus.ACCEPTED);
    }
}