package com.teduscheduler.controller;

import com.teduscheduler.entity.TimetableRequest;
import com.teduscheduler.entity.TimetableResponse;
import com.teduscheduler.service.TimetableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/timetable")
public class TimetableController {

    @Autowired
    private TimetableService timetableService;

    private static final Logger logger = LoggerFactory.getLogger(TimetableController.class);

    @PostMapping("/generate")
    private ResponseEntity<?> generateTimetable(@RequestBody TimetableRequest timetableRequest){
        if (timetableRequest.getCourseCodes() == null ||
                (timetableRequest.getCourseCodes() != null && timetableRequest.getCourseCodes().size() == 0)) {
            String errorMessage = "Courses cannot be empty or zero.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (timetableRequest.getSemester() == null) {
            String errorMessage = "Semester cannot be empty.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        if (timetableRequest.getTimetableFilter() == null) {
            String errorMessage = "TimetableFilter cannot be empty.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        TimetableResponse timetableResponse = timetableService.generateTimetable(timetableRequest);

        if (timetableResponse == null) {
            String errorMessage = "Something went wrong when generating timetable.";
            logger.error(errorMessage);
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(timetableResponse, HttpStatus.OK);
    }
}