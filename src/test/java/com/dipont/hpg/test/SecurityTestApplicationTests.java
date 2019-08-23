package com.dipont.hpg.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityTestApplicationTests {

    @Test
    public void contextLoads() {
        String clientAuth = "018e8163-fc0f-4864-a7be-9098af005ff2";
        String s = new String(Base64.getEncoder().encode(clientAuth.getBytes()));
        System.out.print(URLEncoder.encode("http://localhost"));

        System.out.print(s);
    }

}
