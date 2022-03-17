package com.teduscheduler.service;

import com.teduscheduler.model.Course;
import com.teduscheduler.model.Semester;
import com.teduscheduler.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;


@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private CourseService courseService;

    @Test
    public void findCourseByCourseCodeAndSemesterTest(){
        String courseCode = "CMPE 322";
        Semester semester = new Semester(0, "Fall", "2020", "001");
        Course course = new Course();
        course.setSemester(semester);
        course.setCourseCode(courseCode);

        when(courseRepository.findCourseByCourseCodeAndSemester(courseCode, semester)).thenReturn(null);

        Course dbCourse = courseService.findCourseByCourseCodeAndSemester(courseCode, semester);

        assertNull(dbCourse);

        when(courseRepository.findCourseByCourseCodeAndSemester(courseCode, semester)).thenReturn(course);

        dbCourse = courseService.findCourseByCourseCodeAndSemester(courseCode, semester);

        assertEquals(course, dbCourse);
    }

    @Test
    public void saveTest(){
        String courseCode = "CMPE 322";
        Semester semester = new Semester(0, "Fall", "2020", "001");
        Course course = new Course();
        course.setSemester(semester);
        course.setCourseCode(courseCode);

        when(courseRepository.save(course)).thenReturn(course);

        Course nullCourse = courseService.save(null);

        assertNull(nullCourse);

        when(courseRepository.findCourseByCourseCodeAndSemester(courseCode, semester)).thenReturn(null);
        Course notSavedCourse = courseService.save(course);

        assertEquals(notSavedCourse, course);

        when(courseRepository.findCourseByCourseCodeAndSemester(courseCode, semester)).thenReturn(null);
        Course savedCourse = courseService.save(course);
        assertEquals(savedCourse, course);
    }

}
