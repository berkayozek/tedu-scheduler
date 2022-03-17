package com.teduscheduler.entity;

import com.teduscheduler.model.Course;
import com.teduscheduler.model.Semester;

import java.util.List;

public class TimetableRequest {

    private List<String> courseCodes;

    private Semester semester;

    private TimetableFilter timetableFilter;

    public List<String> getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(List<String> courseCodes) {
        this.courseCodes = courseCodes;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public TimetableFilter getTimetableFilter() {
        return timetableFilter;
    }

    public void setTimetableFilter(TimetableFilter timetableFilter) {
        this.timetableFilter = timetableFilter;
    }
}
