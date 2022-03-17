package com.teduscheduler.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String courseCode;
    private String courseName;
    @OneToOne
    private Semester semester;
    @OneToMany
    private List<Section> sections;

    public Course(){
        this.sections = new ArrayList<>();
    }

    public Course(String courseCode, String courseName, Semester semester, List<Section> sections) {
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.semester = semester;
        this.sections = sections;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Semester getSemester() {
        return semester;
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public List<Section> getSections() {
        return sections;
    }

    public void setSections(List<Section> sections) {
        this.sections = sections;
    }

    public void addSection(Section section) {
        for (Section mySection : sections) {
            if (section.getSectionCode().equals(mySection.getSectionCode())){
                section.setCourseHours(section.getCourseHours());
                section.setInstructors(section.getInstructors());

                return;
            }
        }

        sections.add(section);
    }

    public boolean equals(Course otherCourse){
        return courseCode.equals(otherCourse.getCourseCode()) && courseName.equals(otherCourse.getCourseName())
                && semester.equals(otherCourse.getSemester()) && sections.equals(otherCourse.getSections());
    }
}
