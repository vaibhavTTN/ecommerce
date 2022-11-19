package com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Login {

    @NotBlank(message = "username cannot be blank")
    private String username;

    @NotBlank(message = "password cannot be blank")
    private String password;
}

