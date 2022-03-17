package com.teduscheduler.service;

import com.teduscheduler.model.Course;
import com.teduscheduler.model.CourseHour;
import com.teduscheduler.repository.CourseHourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseHourService {

    @Autowired
    private CourseHourRepository courseHourRepository;

    public CourseHour save(CourseHour courseHour){
        if (courseHour == null) return null;

        CourseHour dbCourseHour = courseHourRepository.findCourseHourByDayAndAndStartHourAndEndHour(courseHour.getDay(), courseHour.getStartHour(), courseHour.getEndHour());

        if (dbCourseHour != null) {
            if (dbCourseHour.equals(courseHour))
                return dbCourseHour;

            dbCourseHour.setDay(courseHour.getDay());
            dbCourseHour.setStartHour(courseHour.getStartHour());
            dbCourseHour.setEndHour(courseHour.getEndHour());

            return courseHourRepository.save(dbCourseHour);
        }

        return courseHourRepository.save(courseHour);
    }
}
