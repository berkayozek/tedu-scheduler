package com.teduscheduler.service;

import com.teduscheduler.model.Instructor;
import com.teduscheduler.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorService {

    @Autowired
    private InstructorRepository instructorRepository;

    public Instructor save(Instructor instructor) {
        Instructor dbInstructor = instructorRepository.findInstructorByName(instructor.getName());

        if (dbInstructor != null) {

            return dbInstructor;
        }

        return instructorRepository.save(instructor);
    }
}
