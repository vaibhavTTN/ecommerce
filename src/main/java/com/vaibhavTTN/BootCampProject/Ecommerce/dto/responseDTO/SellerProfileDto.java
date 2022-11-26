package com.vaibhavTTN.BootCampProject.Ecommerce.dto.responseDTO;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SellerProfileDto {

  private Long id;
  private String firstName;
  private String lastName;
  private Boolean IsActive;
  private String companyContact;
  private String companyName;
  private String image;
  private String GST;
  private Address address;
}
