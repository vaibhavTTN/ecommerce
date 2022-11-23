package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerUpdateProfileDto {
    private String firstName;

    private String middleName;

    private String lastName;

    private String gst;

    private String companyName;

    private String companyContact;

    private MultipartFile image;
}
