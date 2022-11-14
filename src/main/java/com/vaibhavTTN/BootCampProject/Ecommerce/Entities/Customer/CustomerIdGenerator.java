package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Customer;

import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Customer.customerRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Random;

public class CustomerIdGenerator implements IdentifierGenerator {

    @Autowired
    customerRepository customerRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Random random = new Random();
        long count = random.nextLong(10000);
        return "customerId_0"+count;
    }
}
