package com.teduscheduler.service;

import com.teduscheduler.config.AppConfig;
import com.teduscheduler.model.*;
import com.teduscheduler.repository.RoomRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FetchService {

    @Autowired
    private SemesterService semesterService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private InstructorService instructorService;

    @Autowired
    private SectionService sectionService;

    @Autowired
    private CourseHourService courseHourService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private AppConfig appConfig;

    private List<Thread> threadList = new ArrayList<>();


    private static HashMap<String, String> semesterNameHash = new HashMap<String, String>(){{
        put("001", "Fall");
        put("002", "Spring");
        put("003", "Summer");
    }};

    public boolean fetchCoursesData() {
        try{
            Document doc = Jsoup.connect(appConfig.getOfferedUrl()).get();
            Element semesterDiv = doc.selectFirst("#edit-dersler-years");
            saveSemesters(semesterDiv);

            List<Semester> semesterList = semesterService.findAll();
            for (Semester semester : semesterList) {
                saveCourses(semester);
            }
            return true;
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void saveSemesters(Element semesterDiv){
        for (Element semesterElement : semesterDiv.children()) {
            String parsed[] = semesterElement.val().split("/");

            if (parsed.length > 1 ){
                Semester semester = new Semester();
                int parseIntYear = Integer.parseInt(parsed[0]);
                String semesterName = String.format("%s %d-%d",semesterNameHash.get(parsed[1]), parseIntYear, parseIntYear + 1);

                semester.setYear(parsed[0]); // Year
                semester.setCode(parsed[1]); // Code
                semester.setSemesterName(semesterName); // Semester Name
                semesterService.save(semester);
            }
        }
    }

    private void saveCourses(Semester semester) throws IOException {
        String url = appConfig.getOfferedUrl() + "/" + semester.getYear() + "/" + semester.getCode();
        Document doc = Jsoup.connect(url).get();
        Elements courseDivs = doc.select(".event-row");
        for (Element element : courseDivs) {
            String courseNameDivText = element.children().get(0).children().get(1).text();
            String splittedStr[] = courseNameDivText.split(" - ");

            if (splittedStr.length < 2)
                continue;

            String courseCode = splittedStr[0].split("_")[0];
            String courseName = splittedStr[1].split(",")[0];
            String sectionCode = splittedStr[0];
//                String courseCredit = splittedStr[1].split(",")[1].split("\\)")[1].split("/")[0];

            ArrayList<Instructor> instructors = new ArrayList<>();
            Elements instructorElements = element.child(1).child(1).child(0).children();

            for (Element instructorElement : instructorElements) {
                Instructor instructor = instructorService.save(new Instructor(instructorElement.text()));
                instructors.add(instructor);
            }

            Section section = new Section();
            section.setInstructors(instructors);
            section.setSectionCode(sectionCode);
            section.setSemester(semester);
            generateCourseHoursAndRoom(element, section);

            Course course = courseService.findCourseByCourseCodeAndSemester(courseCode, semester);

            if (course == null) {
                course = new Course();
            }

            course.setCourseName(courseName);
            course.setCourseCode(courseCode);
            course.setSemester(semester);
            course.addSection(sectionService.save(section));

            courseService.save(course);
        }
    }

    private void generateCourseHoursAndRoom(Element element, Section section){
        ArrayList<CourseHour> courseHourList = new ArrayList<>();
        ArrayList<Room> roomList = new ArrayList<>();
        Element timeDivs = element.child(3).child(1);
        for (Element timeDiv: timeDivs.children()) {
            if (timeDiv.children() != null && timeDiv.children().size() == 1) {
                String day = timeDiv.child(0).text();
                String hourSplitted[] = timeDiv.text().split("-");
                String startHour = hourSplitted[0].split(":")[1].replaceAll("\\s+","");
                String endHour = hourSplitted[1].split(":")[0].replaceAll("\\s+","");
                CourseHour courseHour = new CourseHour(day, Integer.parseInt(startHour), Integer.parseInt(endHour));

                courseHourList.add(courseHourService.save(courseHour));
            } else if (timeDiv.children().size() == 0) {
                String roomName = timeDiv.text().replaceAll("/","").replaceAll("\\s+","");
                Room room = new Room(roomName);
                roomList.add(roomService.save(room));
            }
        }
        section.setCourseHours(courseHourList);
        section.setRoom(roomList);
    }
}
