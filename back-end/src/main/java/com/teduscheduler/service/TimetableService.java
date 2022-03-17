package com.teduscheduler.service;

import com.teduscheduler.entity.Timetable;
import com.teduscheduler.entity.TimetableFilter;
import com.teduscheduler.entity.TimetableRequest;
import com.teduscheduler.entity.TimetableResponse;
import com.teduscheduler.model.Course;
import com.teduscheduler.model.CourseHour;
import com.teduscheduler.model.Section;
import com.teduscheduler.model.Semester;
import com.teduscheduler.repository.CourseRepository;
import com.teduscheduler.repository.SemesterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TimetableService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    public TimetableResponse generateTimetable(TimetableRequest timetableRequest){
        List<Timetable> timetables = new ArrayList<>();
        List<Course> dbCourses = new ArrayList<>();
        Timetable currentTimetable = new Timetable();

        Semester dbSemester = semesterRepository.findSemesterByYearAndCode(timetableRequest.getSemester().getYear(),
                timetableRequest.getSemester().getCode());

        for (String courseCode : timetableRequest.getCourseCodes()) {
            Course dbCourse = courseRepository.findCourseByCourseCodeAndSemester(courseCode, dbSemester);
            if (dbCourse == null) {
                return null;
            } else {
                dbCourses.add(dbCourse);
            }
        }

        generateTimetableUtil(timetables, currentTimetable, dbCourses, 0, timetableRequest.getTimetableFilter());

        TimetableResponse timetableResponse = new TimetableResponse(timetables, dbCourses);

        return timetableResponse;
    }

    private void generateTimetableUtil(List<Timetable> timetables, Timetable currentTimetable, List<Course> courses, int depth, TimetableFilter timetableFilter){
        if (courses.size() == depth) {
            if (!handleTimetableFilter(currentTimetable, timetableFilter)){
                timetables.add(currentTimetable);
            }
        } else {
            List<Section> sections = courses.get(depth).getSections();

            for (int i=0; i<sections.size(); i++) {
                currentTimetable.getSections().add(sections.get(i));
                generateTimetableUtil(timetables, new Timetable(currentTimetable), courses, depth + 1, timetableFilter);
                currentTimetable.getSections().remove(currentTimetable.getSections().size() - 1);

                if (courses.get(depth).getCourseCode().equals("TEDU 400"))
                    break;
            }
        }
    }

    private boolean handleTimetableFilter(Timetable timetable, TimetableFilter timetableFilter){
        boolean isTimetableValid = false;

        if (!timetableFilter.isAllowConflict() && hasTimetableConflict(timetable)) {
            isTimetableValid = true;
        }

        if (!isTimetableValid && timetableFilter.isEmptyDay() && !hasTimetableEmptyDay(timetable, timetableFilter)) {
            isTimetableValid = true;
        }

        return isTimetableValid;
    }

    private boolean hasTimetableConflict (Timetable timetable) {
        for (Section section : timetable.getSections()) {
            for (Section otherSection : timetable.getSections()) {
                if (!section.equals(otherSection) && section.hasConflict(otherSection)) {
                    timetable.addConflictSections(section, otherSection);
                    timetable.setConflict(true);
                    return true;
                }
            }
        }

        return false;
    }

    private boolean hasTimetableEmptyDay(Timetable timetable, TimetableFilter timetableFilter) {
        int count = 0;

        HashMap<String, List<CourseHour>> map = new HashMap<String, List<CourseHour>>(){{
            put("Mo", new ArrayList<>());
            put("Tu", new ArrayList<>());
            put("We", new ArrayList<>());
            put("Th", new ArrayList<>());
            put("Fr", new ArrayList<>());
        }};

        for (Section section : timetable.getSections()) {
            for (CourseHour courseHour : section.getCourseHours()) {
                map.get(courseHour.getDay()).add(courseHour);
            }
        }

        for (List<CourseHour> courseHours : map.values()) {
            if (courseHours.size() == 0) {
                count++;
                if (count == timetableFilter.getEmptyDayCount()){
                    return true;
                }
            }
        }

        return false;
    }
}