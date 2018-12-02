package myProg.services;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

class SecurUserServiceTest {

    @Test
    void generatePassForUsername() {
        String username="admin";
        //BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String pass = passwordEncoder.encode("user");
        System.out.println(pass);
    }

}