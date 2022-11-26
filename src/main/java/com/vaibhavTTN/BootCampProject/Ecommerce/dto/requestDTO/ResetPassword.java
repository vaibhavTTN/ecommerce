package com.vaibhavTTN.BootCampProject.Ecommerce.dto.requestDTO;

import javax.validation.constraints.Email;
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
public class ResetPassword {

  @NotBlank(
      message = "Email cannot be blank"
  )
  @Email(
      regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
      flags = Pattern.Flag.CASE_INSENSITIVE,
      message = "Email format is Incorrect"
  )
  private String email;

  @NotBlank(message = "Token cannot be blank")
  private String token;

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
