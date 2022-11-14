package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Address;

import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Address.addressRepository;
import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Random;

public class AddressIdGenerator implements IdentifierGenerator {

    @Autowired
    addressRepository addressRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Random random = new Random();
        long count = random.nextLong(10000);
        return "addressId_0"+count;
    }
}
