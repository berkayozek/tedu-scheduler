package com.teduscheduler.entity;

import com.teduscheduler.model.Course;

import java.util.List;

public class TimetableResponse {
    private List<Timetable> timetables;
    private List<Course> selectedCourses;

    public TimetableResponse(List<Timetable> timetables, List<Course> selectedCourses) {
        this.timetables = timetables;
        this.selectedCourses = selectedCourses;
    }

    public List<Timetable> getTimetables() {
        return timetables;
    }

    public void setTimetables(List<Timetable> timetables) {
        this.timetables = timetables;
    }

    public List<Course> getSelectedCourses() {
        return selectedCourses;
    }

    public void setSelectedCourses(List<Course> selectedCourses) {
        this.selectedCourses = selectedCourses;
    }
}
