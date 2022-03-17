package com.teduscheduler.repository;

import com.teduscheduler.model.Instructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.util.List;

@Repository
public interface InstructorRepository extends CrudRepository<Instructor, Integer> {

    Instructor findInstructorByName(String Name);

    @Query(value = "select i.name from instructor as i, section_instructors as si where si.section_id = ?1 and si.instructors_id = i.id", nativeQuery = true)
    List<Tuple> findInstructorNameBySectionId(int sectionId);
}
