package com.teduscheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.teduscheduler.service.CourseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
public class CourseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseService courseService;

    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    private ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

    @Test
    public void emptyCourseTest() throws Exception {
        String year = "";
        String code = "";
        when(courseService.findCourseBySemesterAndYear(year, code)).thenReturn(new ArrayList<>());

        this.mockMvc.perform(get("/api//course/get/" + year + "-" + code)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Year or Semester Code cannot be empty."));
    }

    @Test
    public void nullCourseListFrom() throws Exception {
        String year = "2021";
        String code = "003";

        when(courseService.findCourseBySemesterAndYear(year, code)).thenReturn(null);

        this.mockMvc.perform(get("/api/course/get/" + year + "-" + code)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string(String.format("There is no data related to given %s (year) and %s (code)",year, code)));
    }
}
