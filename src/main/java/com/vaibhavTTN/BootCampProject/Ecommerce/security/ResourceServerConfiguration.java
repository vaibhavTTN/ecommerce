package com.vaibhavTTN.BootCampProject.Ecommerce.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;


@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

//  @Autowired
//  AuthenticationEntryPoint authEntryPoint;
  @Autowired
  UserDetailsService userDetailsService;

  public ResourceServerConfiguration() {
    super();
  }

  @Bean
  public static BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
    return authenticationProvider;
  }

  @Autowired
  public void configureGlobal(final AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(authenticationProvider());
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/**").permitAll()
        .mvcMatchers(HttpMethod.GET, "/register/**").permitAll()
        .mvcMatchers(HttpMethod.POST, "/register/customer").permitAll()
        .mvcMatchers(HttpMethod.POST, "/register/seller").permitAll()
        .mvcMatchers(HttpMethod.PUT, "/register/verify/**").permitAll()
        .mvcMatchers(HttpMethod.POST, "/register/re-verify/**").permitAll()
        .mvcMatchers(HttpMethod.POST, "/forget-password").permitAll()
        .mvcMatchers(HttpMethod.PUT, "/reset-password").permitAll()
        .mvcMatchers(HttpMethod.GET, "/admin/**").permitAll()
        .mvcMatchers(HttpMethod.PATCH, "/admin/**").permitAll()
        .mvcMatchers(HttpMethod.PUT, "/admin/**").permitAll()
//        .anyRequest().authenticated()
        .anyRequest().permitAll()
        .and()
//        .exceptionHandling()
//        .authenticationEntryPoint(authEntryPoint)
//        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .csrf().disable().logout().logoutSuccessUrl("/");
  }

}