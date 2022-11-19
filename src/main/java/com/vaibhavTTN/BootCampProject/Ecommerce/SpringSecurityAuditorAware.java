package com.vaibhavTTN.BootCampProject.Ecommerce;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    return Optional.ofNullable("ADMIN").filter(s -> !s.isEmpty());
  }
}