package com.teduscheduler.service;

import com.teduscheduler.model.Section;
import com.teduscheduler.repository.CourseHourRepository;
import com.teduscheduler.repository.CourseRepository;
import com.teduscheduler.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectionService {

    @Autowired
    private SectionRepository sectionRepository;

    public Section save(Section section) {
        Section dbSection = sectionRepository.findSectionBySectionCodeAndSemester(section.getSectionCode(), section.getSemester());

        if (dbSection != null) {
            if (dbSection.equals(section))
                return dbSection;

            dbSection.setCourseHours(section.getCourseHours());
            dbSection.setInstructors(section.getInstructors());

            return sectionRepository.save(dbSection);
        }

        return sectionRepository.save(section);
    }
}
