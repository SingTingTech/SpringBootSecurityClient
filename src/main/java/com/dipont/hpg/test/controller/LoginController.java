package com.dipont.hpg.test.controller;

import com.dipont.hpg.test.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author alex
 * @date 2019/8/27  17:05
 */
@RestController
@CrossOrigin
public class LoginController {
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserDetailsManager userDetailsManager;

    @PermitAll
    @GetMapping("token")
    @CrossOrigin
    public String getToken(@RequestParam String code,@Value("${auth-server-url}")String authServer){
        //code换取 jwt
        String tokenUrl = authServer + "/oauth/token";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        String body = "grant_type=authorization_code&code="+code+"&redirect_uri=http://192.168.60.38&client_id=CrmSys&client_secret=123456";
        HttpEntity<String> request = new HttpEntity<>(body,headers);


        Map result = restTemplate.postForObject(tokenUrl, request, Map.class);
        String accessToken = (String)result.get("access_token");
        String userName = null;
        if(accessToken!=null){
            String userInfoUrl = authServer + "/user-info";
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setBearerAuth(accessToken);
            HttpEntity<MultiValueMap<String,String>> userRequest = new HttpEntity<>(new LinkedMultiValueMap<>(),httpHeaders);
            ResponseEntity<Map> userInfo = restTemplate.exchange(userInfoUrl,HttpMethod.GET,userRequest,Map.class);
            userName = (String)((Map)userInfo.getBody().get("principal")).get("username");
        }
        return jwtTokenUtil.createToken(userName);
    }

    @GetMapping("user")
    @CrossOrigin
    public UserDetails user(){
        return (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

//    @PostMapping("logout")
//    @CrossOrigin
//    public void logout(){
//        SecurityContextHolder.getContext().setAuthentication(null);
//    }

}
