package com.estrada.userservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced //para que el rest template funcione desde ureka
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

}
