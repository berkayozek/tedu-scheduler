package com.teduscheduler.repository;

import com.teduscheduler.model.Course;
import com.teduscheduler.model.Semester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Integer> {

    Course findCourseByCourseCodeAndSemester(String CourseCode, Semester semester);

    List<Course> findCoursesBySemester(Semester semester);

    Course findCourseById(int Id);

    @Query(value = "select course_code from course where semester_id = ?1 order by course_code asc", nativeQuery = true)
    List<String> findCoursesBySemester(int semesterId);
}
