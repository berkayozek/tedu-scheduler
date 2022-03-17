package com.teduscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.util.List;

@Entity
public class Section {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String sectionCode;

    @ManyToMany
    private List<CourseHour> courseHours;

    @ManyToMany
    private List<Instructor> instructors;

    @ManyToOne
    private Semester semester;

    @ManyToMany
    private List<Room> room;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSectionCode() {
        return sectionCode;
    }

    public void setSectionCode(String sectionCode) {
        this.sectionCode = sectionCode;
    }

    public List<CourseHour> getCourseHours() {
        return courseHours;
    }

    public void setCourseHours(List<CourseHour> courseHours) {
        this.courseHours = courseHours;
    }

    public List<Instructor> getInstructors() {
        return instructors;
    }

    public void setInstructors(List<Instructor> instructors) {
        this.instructors = instructors;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Room> getRoom() {
        return room;
    }

    public void setRoom(List<Room> room) {
        this.room = room;
    }

    public boolean hasConflict(Section otherSection) {
        for (CourseHour courseHour : courseHours) {
            for (CourseHour otherCourseHour : otherSection.getCourseHours()) {
                if (courseHour.getDay().equals(otherCourseHour.getDay())
                        && ((courseHour.getStartHour() >= otherCourseHour.getStartHour() && courseHour.getStartHour() <= otherCourseHour.getEndHour())
                        || (courseHour.getEndHour() >= otherCourseHour.getStartHour() && courseHour.getStartHour() <= otherCourseHour.getStartHour())))
                    return true;
            }
        }

        return false;
    }

    public boolean equals(Section otherSection){
        return sectionCode.equals(otherSection.getSectionCode()) && courseHours.equals(otherSection.getCourseHours())
                && instructors.equals(otherSection.getInstructors()) && semester.equals(otherSection.getSemester());
    }
}
