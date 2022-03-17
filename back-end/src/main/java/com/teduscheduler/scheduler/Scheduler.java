package com.teduscheduler.scheduler;

import com.teduscheduler.config.*;
import com.teduscheduler.service.FetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Component
public class Scheduler {

    @Autowired
    private AppConfig appConfig;

    /*@PostConstruct
    public void onStartup() {
        updateData();
    }*/


    //@Scheduled(cron="* * */2 * * *")
    /*
    public void onSchedule() {
        updateData();
    }
    */
    private void updateData(){
        final String uri = appConfig.getApiUrl() + "/fetch/data";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<String>(appConfig.getFetchSecretKey(), headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            ResponseEntity<String> responseEntityStr = restTemplate.postForEntity(uri, request, String.class);
        } catch (Exception e) { }
    }
}
