package com.teduscheduler.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
    @Value("${tedu-scheduler.course.url}")
    private String offeredUrl;

    @Value("${tedu-scheduler.fetch-secret-key}")
    private String fetchSecretKey;

    @Value("${tedu-scheduler.url}")
    private String apiUrl;

    @Bean
    public String getOfferedUrl(){
        return offeredUrl;
    }

    @Bean
    public String getFetchSecretKey(){
        return fetchSecretKey;
    }

    @Bean
    public String getApiUrl(){
        return apiUrl;
    }
}
