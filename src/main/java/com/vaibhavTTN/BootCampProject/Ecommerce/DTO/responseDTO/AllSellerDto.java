package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllSellerDto extends RepresentationModel<AllSellerDto> {

  private Long id;
  private String fullName;
  private String email;
  private Boolean IsActive;
  private String companyName;
  private String companyContact;
  private Address address;
}
