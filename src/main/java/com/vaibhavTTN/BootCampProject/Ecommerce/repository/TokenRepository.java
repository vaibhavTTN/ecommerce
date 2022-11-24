package com.vaibhavTTN.BootCampProject.Ecommerce.repository;

import com.vaibhavTTN.BootCampProject.Ecommerce.entities.ConfirmationToken;
import com.vaibhavTTN.BootCampProject.Ecommerce.entities.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<ConfirmationToken, Long> {

  Optional<ConfirmationToken> findByToken(String token);

  Optional<ConfirmationToken> findByUser(User user);

}
