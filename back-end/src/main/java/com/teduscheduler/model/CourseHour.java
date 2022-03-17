package com.teduscheduler.model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CourseHour {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String day;
    private int startHour;
    private int endHour;

    public CourseHour() {

    }

    public CourseHour(String day, int startHour, int endHour) {
        this.day = day;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }

    public boolean equals(CourseHour otherCourseHour){
        return day.equals(otherCourseHour.getDay()) && startHour == otherCourseHour.getStartHour()
                && endHour == otherCourseHour.getEndHour();
    }
}
