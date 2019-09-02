package com.dipont.hpg.test.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * @author alex
 * @date 2019/8/27  17:10
 */
@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate getRestTemplate(){

        RestTemplate restTemplate = new RestTemplate();
        HttpMessageConverter<String> stringConverter = new StringHttpMessageConverter();
        FormHttpMessageConverter formConverter = new FormHttpMessageConverter();
        List<HttpMessageConverter<?>> msgConverters = new ArrayList<>();
        msgConverters.add(formConverter);
        msgConverters.add(new MappingJackson2HttpMessageConverter());
        msgConverters.add(stringConverter);
        restTemplate.setMessageConverters(msgConverters);
        return restTemplate;
    }
}
