package com.dipont.hpg.test.controller;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author alex
 * @date 2019/8/20  11:25
 */
@RestController
public class TestController {
    @GetMapping("time")
    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.now().format(formatter);
    }
    @GetMapping("name")
    public String getName(){
        return "HpgSys";
    }

    @RequestMapping("/user")
    public Principal user(Principal principal) {
        return principal;
    }

}
