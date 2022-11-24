package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllCustomerDto extends RepresentationModel<AllCustomerDto> {

  private Long id;
  private String fullName;
  private String email;
  private boolean IsActive;
  private String contact;
}
