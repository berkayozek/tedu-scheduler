package com.teduscheduler.service;

import com.teduscheduler.model.Semester;
import com.teduscheduler.repository.CourseRepository;
import com.teduscheduler.repository.SemesterRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

@ExtendWith(MockitoExtension.class)
public class SemesterServiceTest {

    @Mock
    private SemesterRepository semesterRepository;

    @InjectMocks
    private SemesterService semesterService;

    @Test
    public void saveTest(){
        Semester semester = new Semester(0, "Fall", "2020", "001");

        when(semesterRepository.save(semester)).thenReturn(semester);
        when(semesterRepository.findSemesterByYearAndCode("2020", "001")).thenReturn(null);
        Semester notSavedSemester = semesterService.save(semester);

        assertTrue(notSavedSemester.equals(new Semester(0, "Fall", "2020", "001")));

        when(semesterRepository.findSemesterByYearAndCode("2020", "001")).thenReturn(semester);
        Semester savedSemester = semesterService.save(semester);
        assertTrue(savedSemester.equals(new Semester(0, "Fall", "2020", "001")));
    }
}
