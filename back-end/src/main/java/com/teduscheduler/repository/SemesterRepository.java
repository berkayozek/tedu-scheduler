package com.teduscheduler.repository;

import com.teduscheduler.model.Semester;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SemesterRepository extends CrudRepository<Semester, Integer> {

    List<Semester> findAllBy(Sort sort);

    //Sort This by year and code
    Semester findSemesterByYearAndCode(String Year, String Code);
}
