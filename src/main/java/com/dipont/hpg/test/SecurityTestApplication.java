package com.dipont.hpg.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collections;
import java.util.stream.Collectors;

@SpringBootApplication
public class SecurityTestApplication implements CommandLineRunner {

    @Autowired
    UserDetailsManager userDetailsManager;
    @Autowired
    PasswordEncoder encoder;

    @Override
    public void run(String... args) throws Exception {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("USER");

        User user = new User("cris",encoder.encode("root"), Collections.singletonList(authority));
        userDetailsManager.createUser(user);
    }

    public static void main(String[] args) {
        SpringApplication.run(SecurityTestApplication.class, args);
    }

}
