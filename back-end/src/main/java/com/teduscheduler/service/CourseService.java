package com.teduscheduler.service;

import com.teduscheduler.model.Course;
import com.teduscheduler.model.Instructor;
import com.teduscheduler.model.Section;
import com.teduscheduler.model.Semester;
import com.teduscheduler.repository.CourseRepository;
import com.teduscheduler.repository.InstructorRepository;
import com.teduscheduler.repository.SectionRepository;
import com.teduscheduler.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.Tuple;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private InstructorRepository instructorRepository;

    public Course findCourseByCourseCodeAndSemester(String courseCode, Semester semester) {
        Course dbCourse = courseRepository.findCourseByCourseCodeAndSemester(courseCode, semester);

        if (dbCourse == null) {
            return null;
        }

        return dbCourse;
    }

    public List<String> findCourseBySemesterAndYear(String year, String code) {
        Semester dbSemester = semesterRepository.findSemesterByYearAndCode(year, code);

        if (dbSemester == null) {
            return null;
        }

        return courseRepository.findCoursesBySemester(dbSemester.getId());
    }

    public List<Course> getAllCourses(){
        return (List<Course>) courseRepository.findAll();
    }

    public Course save(Course course){
        if (course == null) return null;

        Course dbCourse = courseRepository.findCourseByCourseCodeAndSemester(course.getCourseCode(), course.getSemester());

        if (dbCourse != null) {
            if (dbCourse.equals(course))
                return dbCourse;

            dbCourse.setCourseName(course.getCourseName());
            dbCourse.setSemester(course.getSemester());
            dbCourse.setSections(course.getSections());

            return courseRepository.save(dbCourse);
        }

        return courseRepository.save(course);
    }
}
