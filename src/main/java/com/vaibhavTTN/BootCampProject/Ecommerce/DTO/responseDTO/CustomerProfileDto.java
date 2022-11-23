package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private Boolean IsActive;
    private String contact;
    private String image;
    private List<Address> address;
}
