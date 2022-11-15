package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.RequestDTO;

import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Component
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDetails {

    @NotBlank
    @Email(
            regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "Email format is Incorrect"
    )
    private String email;

    @NotBlank
    private String firstName;

    private String middleName;

    @NotBlank
    private String lastName;

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

    @NotBlank
    @Size(min=10,max = 10)
    private String contact;
}
