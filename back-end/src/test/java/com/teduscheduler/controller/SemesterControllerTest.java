package com.teduscheduler.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.teduscheduler.service.SemesterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SemesterController.class)
public class SemesterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SemesterService semesterService;

    private ObjectMapper objectMapper = new ObjectMapper().configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    private ObjectWriter ow = objectMapper.writer().withDefaultPrettyPrinter();


}
