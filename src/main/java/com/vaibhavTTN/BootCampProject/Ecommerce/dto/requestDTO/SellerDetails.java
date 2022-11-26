package com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SellerDetails {

  @NotBlank(message = "Email cannot be blank")
  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
  private String email;

  @NotBlank(message = "FirstName cannot be blank")
  private String firstName;

  private String middleName;

  @NotBlank(message = "LastName cannot be blank")
  private String lastName;

  @NotBlank(message = "Password cannot be blank")
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
      message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
  )
  private String password;

  @NotBlank(message = "Confirm password cannot be blank")
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
      message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
  )
  private String confirmPassword;

  @NotBlank(message = "GST cannot be blank")
  private String gst;

  @NotBlank(message = "Company Name cannot be blank")
  private String companyName;

  @NotBlank(message = "Company contact cannot be blank")
  @Size(min = 10, max = 10, message = "Company contact must be valid")
  private String companyContact;

  @NotBlank(message = "City cannot be blank")
  private String city;

  @NotBlank(message = "State cannot be blank")
  private String state;

  @NotBlank(message = "Country cannot be blank")
  private String country;

  @NotBlank(message = "AddressLine cannot be blank")
  private String addressLine;

  @NotBlank(message = "Zipcode cannot be blank")
  private String zipCode;

  private String label;
}
