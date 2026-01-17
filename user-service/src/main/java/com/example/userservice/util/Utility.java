package com.example.userservice.util;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

@Configuration
public class Utility {

    @Bean
    public String createEventId(){
        return UUID.randomUUID().toString();
    }
}
