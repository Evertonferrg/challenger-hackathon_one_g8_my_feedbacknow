package com.fedbacknow.fedbacknow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SentimentConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(); // Cliente HTTP para chamadas externas
    }
}

