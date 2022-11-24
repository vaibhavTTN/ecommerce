package com.vaibhavTTN.BootCampProject.Ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
//@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
@EnableScheduling
public class EcommerceApplication {


  public static void main(String[] args) {
    SpringApplication.run(EcommerceApplication.class, args);
  }

//	@Bean
//	public ModelMapper modelMapper() {
//		return new ModelMapper();
//	}
}
