package com.examsystem;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class ExamSystemApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    public void contextLoads() {
        BCryptPasswordEncoder passwordEncoder1 = new BCryptPasswordEncoder();
        String encode = passwordEncoder1.encode("123456");
        System.out.println(encode);
    }

}
