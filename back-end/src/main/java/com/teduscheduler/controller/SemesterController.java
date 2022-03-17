package com.teduscheduler.controller;

import com.teduscheduler.model.Semester;
import com.teduscheduler.service.SemesterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/semester")
public class SemesterController {

    @Autowired
    private SemesterService semesterService;

    private static final Logger logger = LoggerFactory.getLogger(SemesterController.class);

    @GetMapping("/getall")
    public ResponseEntity<?> getAllSemesters(){

        List<Semester> semesterList = semesterService.findAll();

        return new ResponseEntity<>(semesterList, HttpStatus.OK);
    }
}
