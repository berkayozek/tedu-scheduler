package com.teduscheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.teduscheduler.entity.TimetableFilter;
import com.teduscheduler.entity.TimetableRequest;
import com.teduscheduler.entity.TimetableResponse;
import com.teduscheduler.model.Course;
import com.teduscheduler.model.Semester;
import com.teduscheduler.service.TimetableService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import static org.mockito.Mockito.when;

@WebMvcTest(TimetableController.class)
public class TimetableControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TimetableService timetableService;

    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    private ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();

    @Test
    public void emptyCourseTest() throws Exception {
        TimetableRequest timetableRequest = new TimetableRequest();
        String requestJson=ow.writeValueAsString(timetableRequest);

        TimetableResponse timetableResponse = new TimetableResponse(new ArrayList<>(), new ArrayList<>());

        when(timetableService.generateTimetable(timetableRequest)).thenReturn(timetableResponse);

        this.mockMvc.perform(post("/api//timetable/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Courses cannot be empty or zero."));
    }

    @Test
    public void emptySemester() throws Exception {
        ArrayList<String> courseCodes = new ArrayList<>();
        courseCodes.add("CMPE 360");
        TimetableFilter timetableFilter = new TimetableFilter();


        TimetableRequest timetableRequest = new TimetableRequest();
        timetableRequest.setCourseCodes(courseCodes);
        timetableRequest.setTimetableFilter(timetableFilter);

        String requestJson=ow.writeValueAsString(timetableRequest);

        when(timetableService.generateTimetable(timetableRequest)).thenReturn(null);

        this.mockMvc.perform(post("/api//timetable/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Semester cannot be empty."));
    }

    @Test
    public void emptyTimetableFilter() throws Exception {
        ArrayList<String> courseCodes = new ArrayList<>();
        courseCodes.add("CMPE 360");
        TimetableFilter timetableFilter = new TimetableFilter();


        TimetableRequest timetableRequest = new TimetableRequest();
        timetableRequest.setCourseCodes(courseCodes);
        timetableRequest.setSemester(new Semester());

        String requestJson=ow.writeValueAsString(timetableRequest);

        when(timetableService.generateTimetable(timetableRequest)).thenReturn(null);

        this.mockMvc.perform(post("/api//timetable/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("TimetableFilter cannot be empty."));
    }

    @Test
    public void nullTimetableResponse() throws Exception {
        ArrayList<String> courseCodes = new ArrayList<>();
        courseCodes.add("CMPE 360");
        TimetableFilter timetableFilter = new TimetableFilter();


        TimetableRequest timetableRequest = new TimetableRequest();
        timetableRequest.setCourseCodes(courseCodes);
        timetableRequest.setSemester(new Semester());
        timetableRequest.setTimetableFilter(timetableFilter);

        String requestJson=ow.writeValueAsString(timetableRequest);

        when(timetableService.generateTimetable(timetableRequest)).thenReturn(null);

        this.mockMvc.perform(post("/api//timetable/generate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Something went wrong when generating timetable."));
    }
}