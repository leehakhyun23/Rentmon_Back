package com.himedia.rentmon_back;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class RentmonBackApplicationTests {

    @Test
    void contextLoads() {
        PasswordEncoder ps = new BCryptPasswordEncoder();
        System.out.println(ps.encode("kakao"));
    }

}
