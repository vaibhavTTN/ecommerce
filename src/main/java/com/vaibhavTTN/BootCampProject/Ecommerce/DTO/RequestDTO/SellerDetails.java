package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class SellerDetails {
    private String email;
    private String firstName;
    private String middleName;
    private String lastName;
    private String password;
    private String gst;
    private String companyName;
    private String companyContact;
}
