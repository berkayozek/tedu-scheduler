package com.teduscheduler.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Entity;

@Entity
public class Semester {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String semesterName;
    private String year;
    private String code;

    public Semester() { }

    public Semester(int id, String semesterName, String year, String code) {
        this.id = id;
        this.semesterName = semesterName;
        this.year = year;
        this.code = code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean equals(Semester otherSemester){
        return semesterName.equals(otherSemester.getSemesterName()) && year.equals(otherSemester.getYear())
                && code.equals(otherSemester.getCode());
    }

}
