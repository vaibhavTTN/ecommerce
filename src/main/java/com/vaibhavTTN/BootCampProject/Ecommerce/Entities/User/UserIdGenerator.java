package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.User;

import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.User.userRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Random;

public class UserIdGenerator implements IdentifierGenerator {

    @Autowired
    userRepository userRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Random random = new Random();
        long count = random.nextLong(10000);
        return "userId_0"+count;
    }
}
