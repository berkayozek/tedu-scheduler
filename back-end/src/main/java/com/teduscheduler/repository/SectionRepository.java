package com.teduscheduler.repository;

import com.teduscheduler.model.Section;
import com.teduscheduler.model.Semester;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface SectionRepository extends CrudRepository<Section, Integer> {

    Section findSectionBySectionCodeAndSemester(String SectionCode, Semester semester);

    @Query(value = "select s.id, s.section_code from course_sections as cs, section as s where cs.course_id = ?1 and s.id = cs.sections_id", nativeQuery = true)
    List<Tuple> findSectionsIdAndCodeByCourseId(int courseId);
}
