package com.vaibhavTTN.BootCampProject.Ecommerce.service;

import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.CustomerUpdateProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.requestDTO.UpdatePassword;
import com.vaibhavTTN.BootCampProject.Ecommerce.DTO.responseDTO.CustomerProfileDto;
import com.vaibhavTTN.BootCampProject.Ecommerce.Utilities.EmailSenderService;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Address;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.Customer;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.CustomException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.PasswordMatchException;
import com.vaibhavTTN.BootCampProject.Ecommerce.exceptionHandling.UserNotFoundException;
import com.vaibhavTTN.BootCampProject.Ecommerce.properties.ApplicationProperties;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.AddressRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.CustomerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.repository.UserRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    EmailSenderService emailSenderService;

    @Autowired
    ApplicationProperties applicationProperties;

    public CustomerProfileDto getCustomerProfile(Authentication authentication) {
        CustomerProfileDto customerProfileDto = new CustomerProfileDto();
        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException("invalid email"));

        Customer customer = customerRepository.findByUser(user)
            .orElseThrow(() -> new CustomException("Email invalid"));

        customerProfileDto.setId(user.getId());
        customerProfileDto.setFirstName(user.getFirstName());
        customerProfileDto.setLastName(user.getLastName());
        customerProfileDto.setAddress(user.getAddress());
        customerProfileDto.setContact(customer.getContact());
        customerProfileDto.setImage(
            applicationProperties.getUrl() +File.separator+ "customer" + File.separator + "image");
        customerProfileDto.setIsActive(user.getIsActive());

        return customerProfileDto;
    }

    public String updateCustomerPassword(
        Authentication authentication,
        UpdatePassword updatePassword
    ) {
        String username = authentication.getName();

        User user = userRepository.findByEmail(username)
            .orElseThrow(() -> new UserNotFoundException("invalid email"));

        if (!bCryptPasswordEncoder.matches(updatePassword.getCurrentPassword(),
            user.getPassword())) {
            throw new PasswordMatchException("invalid Credential");
        }

        if (!updatePassword.getPassword().equals(updatePassword.getConfirmPassword())) {
            throw new PasswordMatchException("password does not matched with confirm password");
        }

        user.setPassword(bCryptPasswordEncoder.encode(updatePassword.getConfirmPassword()));
        userRepository.save(user);

        try {
            emailSenderService.sendCustomEmail(
                user,
                "Password Updated !",
                "Dear " + user.getFirstName() + " \n Your password is Updated."
            );
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        return "Your Password is Updated SuccessFully";
    }

    public String updateCustomerAddress(
        Authentication authentication,
        Long id,
        Address requestAddress
    ) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new UserNotFoundException("invalid email"));

        Address address = user.getAddress().stream().filter(e -> e.getId() == id)
            .findFirst()
            .orElseThrow(() -> new CustomException("Address not found"));

        if (requestAddress.getAddressLine() != null) {
            address.setAddressLine(requestAddress.getAddressLine());
        }

        if (requestAddress.getCity() != null) {
            address.setCity(requestAddress.getCity());
        }

        if (requestAddress.getCity() != null) {
            address.setCity(requestAddress.getCity());
        }

        if (requestAddress.getLabel() != null) {
            address.setLabel(requestAddress.getLabel());
        }

        if (requestAddress.getState() != null) {
            address.setState(requestAddress.getState());
        }

        if (requestAddress.getZipCode() != null) {
            address.setZipCode(requestAddress.getZipCode());
        }

        addressRepository.save(address);

        return "Address is updated successfully";
    }

    public String deleteCustomerProfileAddress(
        Authentication authentication,
        Long id
    ) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new CustomException("Email invalid"));

        Address address = user.getAddress().stream()
            .filter(e -> e.getId() == id)
            .findFirst()
            .orElseThrow(()->new CustomException("Address not found"));

        List<Address> addressList = user.getAddress().stream()
            .filter(e->e.getId()!=id)
            .toList();

        user.setAddress(addressList);

        addressRepository.delete(address);

        return "Address deleted successfully";
    }

    public String updateCustomerProfileImage(
        Authentication authentication,
        MultipartFile file
    ) throws IOException {

        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new CustomException("Email invalid"));

        String name = file.getOriginalFilename();
        String extension = "";
        String fileName = file.getOriginalFilename();

        int index = fileName.lastIndexOf('.');
        if (index > 0) {
            extension = fileName.substring(index + 1);
            System.out.println("File extension is " + extension);
        }

        if (extension.equals("")) {
            throw new CustomException("File must contain Extension");
        }

        switch (extension) {
            case "jpg":
                break;
            case "bmp":
                break;
            case "png":
                break;
            case "jpeg":
                break;
            default:
                throw new CustomException("File must contain Extension");
        }

        fileName = applicationProperties.getImageUserPath() + File.separator + user.getId() + "."
            + extension;

        File f = new File(fileName);

        if (f.delete()) {
            logger.debug("fileee deleted");
        }

        File directory = new File(
            System.getProperty("user.dir") + applicationProperties.getImageUserPath());
        if (!directory.exists()) {
            try {
                directory.mkdir();
            } catch (SecurityException se) {
                return null;
            }
        }

        final String path = System.getProperty("user.dir") + fileName;
        try (InputStream inputStream = file.getInputStream();
            FileOutputStream fileOutputStream = new FileOutputStream(new File(path))) {
            byte[] buf = new byte[1024];
            int numRead = 0;
            while ((numRead = inputStream.read(buf)) >= 0) {
                fileOutputStream.write(buf, 0, numRead);
            }
        } catch (Exception e) {
            return null;
        }

        return "Customer Profile image is Updated";
    }

    public String customerUpdateProfile(
        Authentication authentication,
        CustomerUpdateProfileDto customerProfileDto
    ) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new CustomException("Email invalid"));

        Customer customer = customerRepository.findByUser(user)
            .orElseThrow(() -> new CustomException("Email Invalid"));

        if (customerProfileDto.getContact() != null) {
            customer.setContact(customerProfileDto.getContact());
        }

        if (customerProfileDto.getFirstName() != null) {
            user.setFirstName(customerProfileDto.getFirstName());
        }

        if (customerProfileDto.getMiddleName() != null) {
            user.setMiddleName(customerProfileDto.getMiddleName());
        }

        if (customerProfileDto.getLastName() != null) {
            user.setLastName(user.getLastName());
        }

        userRepository.save(user);
        customerRepository.save(customer);

        return "Customer Profile is Updated";
    }

    public String addCustomerAddress(
        Authentication authentication,
        Address address
    ) {
        User user=userRepository.findByEmail(authentication.getName())
            .orElseThrow(
                ()->new UserNotFoundException("No user is present for corresponding email")
        );

        List<Address> addressList=user.getAddress();

        Address address1=new Address();

        address1.setAddressLine(address.getAddressLine());
        address1.setCity(address.getCity());
        address1.setState(address.getState());
        address1.setLabel(address.getLabel());
        address1.setCountry(address.getCountry());
        address1.setZipCode(address.getZipCode());

        addressList.add(address1);

        user.setAddress(addressList);
        userRepository.save(user);

        return "Customer Address is Added";
    }

    public String getImage(Authentication authentication) {
        User user = userRepository.findByEmail(authentication.getName())
            .orElseThrow(() -> new CustomException("Email invalid"));

        File f = new File(System.getProperty("user.dir") + File.separator
            + applicationProperties.getImageUserPath());

        File[] matchingFiles = f.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(user.getId().toString());
            }
        });

        return matchingFiles[0].getName();
    }
}
