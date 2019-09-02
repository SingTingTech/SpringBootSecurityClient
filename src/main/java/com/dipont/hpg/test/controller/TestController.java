package com.dipont.hpg.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin
public class TestController {
    @GetMapping("time")
    @CrossOrigin
    public String getTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
        return LocalDateTime.now().format(formatter);
    }

    @GetMapping("name")
    @CrossOrigin
    public String getName(){
        return "HpgSys";
    }


}
