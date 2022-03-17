package com.teduscheduler.controller;

import com.teduscheduler.config.AppConfig;
import com.teduscheduler.model.*;
import com.teduscheduler.service.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/fetch")
public class FetchController {

    @Autowired
    private AppConfig appConfig;

    @Autowired
    private FetchService fetchService;

    private static final Logger logger = LoggerFactory.getLogger(FetchController.class);


    @PostMapping("/data")
    public ResponseEntity<?> updateData(@RequestBody String fetchSecretKey) throws IOException {
        if (!fetchSecretKey.equals(appConfig.getFetchSecretKey())) {
            String errorMessage = "Wrong Secret Key!";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.FORBIDDEN);
        }
        logger.info("Database update started");

        if (fetchService.fetchCoursesData()) {
            logger.info("Database updated");
            return new ResponseEntity<>("Database updated", HttpStatus.OK);
        } else {
            logger.info("Database couldn't be updated");
            return new ResponseEntity<>("Database couldn't be updated", HttpStatus.BAD_REQUEST);
        }

    }

}
