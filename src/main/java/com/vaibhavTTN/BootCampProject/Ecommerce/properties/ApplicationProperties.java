package com.vaibhavTTN.BootCampProject.Ecommerce.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationProperties {

    @Value("${app.url}")
    private String url;

    @Value("${email.verification.url}")
    private String emailVerifyUrl;

    @Value(("${spring.mail.username}"))
    private String senderEmail;

    @Value(("${admin.email}"))
    private String adminEmail;

    @Value(("${document.seller.image.path}"))
    private String imageSellerPath;

    @Value(("${document.seller.image.path}"))
    private String imageCustomerPath;
}
