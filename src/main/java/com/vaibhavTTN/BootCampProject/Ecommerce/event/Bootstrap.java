package com.vaibhavTTN.BootCampProject.Ecommerce.event;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Role;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.enums.Roles;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.RoleRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

@Component
public class Bootstrap implements ApplicationRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(">>>>>BootStrap file Started>>>>>");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        Long count = roleRepository.count();

        if(count>=1){
            return;
        }

        Role role_admin = new Role();
        role_admin.setId(1L);
        role_admin.setAuthority(Roles.ROLE_ADMIN.toString());
        role_admin.setIsDeleted(false);
        roleRepository.save(role_admin);

        Role role_customer = new Role();
        role_admin.setId(2L);
        role_customer.setAuthority(Roles.ROLE_CUSTOMER.toString());
        role_customer.setIsDeleted(false);
        roleRepository.save(role_customer);

        Role role_seller = new Role();
        role_admin.setId(3L);
        role_seller.setAuthority(Roles.ROLE_SELLER.toString());
        role_seller.setIsDeleted(false);
        roleRepository.save(role_seller);

        User user = new User();
        user.setEmail("admin@gmail.com");
        user.setFirstName("Admin");
        user.setLastName("Admin");
        user.setPassword(bCryptPasswordEncoder.encode("1234"));
        user.setPasswordUpdatedDate(LocalDateTime.now());

        Role roleAdmin = roleRepository.findByAuthority(Roles.ROLE_ADMIN.toString()).get();

        user.setCreatedBy(roleAdmin.getAuthority());
        user.setModifiedBy(roleAdmin.getAuthority());
        user.setModifiedOn(LocalDateTime.now());
        user.setCreatedOn(LocalDateTime.now());
        user.setIsDelete(false);
        user.setIsActive(true);
        user.setIsLocked(false);
        user.setInvalidAttemptCount(0);

        user.setRole(roleAdmin);

        userRepository.save(user);

        System.out.println(">>>>>BootStrap file Stop>>>>>");
    }
}
