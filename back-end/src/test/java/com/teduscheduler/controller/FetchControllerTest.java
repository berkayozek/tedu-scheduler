package com.teduscheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.teduscheduler.config.AppConfig;
import com.teduscheduler.service.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FetchController.class)
public class FetchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SemesterService semesterService;

    @MockBean
    private CourseService courseService;

    @MockBean
    private InstructorService instructorService;

    @MockBean
    private SectionService sectionService;

    @MockBean
    private AppConfig appConfig;

    @MockBean
    private FetchService fetchService;

    @Test
    public void wrongSecretKeyUpdateData() throws Exception {
        String secretKey = "secret-key-app";

        when(appConfig.getFetchSecretKey()).thenReturn("secret-key");

        this.mockMvc.perform(post("/api/fetch/data")
                .content(secretKey))
                .andDo(print()).andExpect(status().isForbidden())
                .andExpect(content().string("Wrong Secret Key!"));
    }

    @Test
    private void correctSecretKey() throws Exception{
        String secretKey = "secret-key-app";

        when(appConfig.getFetchSecretKey()).thenReturn("secret-key-app");
        when(fetchService.fetchCoursesData()).thenReturn(true);

        this.mockMvc.perform(post("/api/fetch/data")
                .content(secretKey))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Database updated"));
    }

    private void correctSecretKeyErrorOccur() throws Exception{
        String secretKey = "secret-key-app";

        when(appConfig.getFetchSecretKey()).thenReturn("secret-key-app");
        when(fetchService.fetchCoursesData()).thenReturn(false);

        this.mockMvc.perform(post("/api/fetch/data")
                .content(secretKey))
                .andDo(print()).andExpect(status().isBadRequest())
                .andExpect(content().string("Database couldn't be updated"));
    }
}
