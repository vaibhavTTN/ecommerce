package com.vaibhavTTN.BootCampProject.Ecommerce.Entities.Seller;

import com.vaibhavTTN.BootCampProject.Ecommerce.Repository.Seller.sellerRepository;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Random;

public class SellerIdGenerator implements IdentifierGenerator {

    @Autowired
    sellerRepository sellerRepository;

    @Override
    public Serializable generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) throws HibernateException {
        Random random = new Random();
        long count = random.nextLong(10000);
        return "sellerId_0"+count;
    }
}
