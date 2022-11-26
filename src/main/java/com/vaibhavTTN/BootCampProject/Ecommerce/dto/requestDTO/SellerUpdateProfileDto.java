package com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

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

}
