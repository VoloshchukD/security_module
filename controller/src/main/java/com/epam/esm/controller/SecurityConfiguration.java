package com.epam.esm.controller;

import com.epam.esm.controller.jwt.JwtConfiguration;
import com.epam.esm.controller.jwt.JwtTokenVerifier;
import com.epam.esm.controller.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.epam.esm.entity.User;
import com.epam.esm.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.SecretKey;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private SecretKey secretKey;

    private JwtConfiguration jwtConfiguration;

    @Autowired
    public SecurityConfiguration(SecretKey secretKey,
                                 JwtConfiguration jwtConfiguration) {
        this.secretKey = secretKey;
        this.jwtConfiguration = jwtConfiguration;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(
                        new JwtUsernameAndPasswordAuthenticationFilter(
                                authenticationManager(), jwtConfiguration, secretKey))
                .addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfiguration),
                        JwtUsernameAndPasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/gift-certificates/**").permitAll()
                .antMatchers(HttpMethod.POST, "/gift-certificates/orders/**").hasAnyRole(
                        User.Role.ADMINISTRATOR.name(), User.Role.USER.name())
                .antMatchers("/").hasRole(User.Role.ADMINISTRATOR.name())
                .antMatchers(HttpMethod.GET).hasRole(User.Role.USER.name())
                .antMatchers(HttpMethod.POST, "/users").permitAll()
                .anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

}
