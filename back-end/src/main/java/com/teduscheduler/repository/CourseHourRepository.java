package com.teduscheduler.repository;

import com.teduscheduler.model.CourseHour;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseHourRepository extends CrudRepository<CourseHour, Integer> {

    CourseHour findCourseHourByDayAndAndStartHourAndEndHour(String day, int startHour, int endHour);
}
