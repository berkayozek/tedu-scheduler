package com.teduscheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@SpringBootApplication
@EnableScheduling
public class TeduschedulerApplication {

    public static void main(String[] args) {
        System.setProperty("jsse.enableSNIExtension", "false");

        SpringApplication.run(TeduschedulerApplication.class, args);
    }

}
