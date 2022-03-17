package com.teduscheduler.controller;

import com.teduscheduler.model.Course;
import com.teduscheduler.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/course")
public class CourseController {
    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @GetMapping("/get/{year}-{code}")
    public ResponseEntity<?> GetCourse(@PathVariable String year, @PathVariable String code){
        if (year.isEmpty() || code.isEmpty()) {
            String errorMessage = "Year or Semester Code cannot be empty.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        List <String> courses = courseService.findCourseBySemesterAndYear(year, code);

        if (courses == null) {
            String errorMessage = String.format("There is no data related to given %s (year) and %s (code)",year, code);
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
