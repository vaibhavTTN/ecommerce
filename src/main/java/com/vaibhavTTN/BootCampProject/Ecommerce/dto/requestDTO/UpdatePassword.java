package com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePassword {

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
      message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
  )
  private String currentPassword;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
      message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
  )
  private String password;

  @NotBlank
  @Pattern(
      regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()])(?=\\S+$).{8,20}$",
      message = "Password must contain one UpperCase, one LowerCase, one Special Character and one Number"
  )
  private String confirmPassword;
}
