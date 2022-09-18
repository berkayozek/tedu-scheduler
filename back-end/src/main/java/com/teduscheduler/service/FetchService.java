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
import java.io.File;
import java.io.FileInputStream;  
import java.io.FileNotFoundException;  
import java.io.IOException;    
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.ss.usermodel.Cell;  
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;
import java.util.ArrayList;
import java.util.Arrays;
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
            Element semesterDiv = doc.selectFirst("#edit-semestr-all");
            saveSemesters(semesterDiv);

            List<Semester> semesterList = semesterService.findAll();
            for (Semester semester : semesterList) {
                readCourses(semester);
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

    private void readCourses(Semester semester) throws IOException {
        FileInputStream file = new FileInputStream(new File(semester.getYear() + "_" + semester.getCode() + ".xls"));
        Workbook wb = null;
        try
        {
            wb = new HSSFWorkbook(file);
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
        }
        catch(IOException io)
        {
            io.printStackTrace();
        }
        Sheet sheet = wb.getSheetAt(0);
        int max = sheet.getLastRowNum()+1;
        for (int i=1; i<max; i++) {
            String courseCode = ReadCellData(sheet, i, 1);
            String courseName = ReadCellData(sheet, i, 2);
            String sectionCode = ReadCellData(sheet, i, 3);
            String teachers = ReadCellData(sheet, i, 11);
            String rooms = ReadCellData(sheet, i, 12);
            String hours = ReadCellData(sheet, i, 13);
            System.out.println("Processing " + sectionCode + " for semester " + semester.getYear() + "/" + semester.getCode());
            List<String> stringList = new ArrayList<String>(Arrays.asList(teachers.split(",")));
            ArrayList<Instructor> instructors = new ArrayList<>();
            for (String a : stringList) {
                Instructor instructor = instructorService.save(new Instructor(a));
                instructors.add(instructor);
            }
            Section section = new Section();
            section.setInstructors(instructors);
            section.setSectionCode(sectionCode);
            section.setSemester(semester);
            generateCourseHoursAndRoom(section, rooms, hours);
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

    public String ReadCellData(Sheet sheet, int vRow, int vColumn) {
        Row row = sheet.getRow(vRow);
        Cell cell = row.getCell(vColumn);
        if (cell == null) {
            return "";
        } else {
            return cell.getStringCellValue();
        }
    }

    private void generateCourseHoursAndRoom(Section section, String rooms, String hours){
        ArrayList<CourseHour> courseHourList = new ArrayList<>();
        ArrayList<Room> roomList = new ArrayList<>();
        List<String> roomlist = new ArrayList<String>(Arrays.asList(rooms.split(" ")));
        for (String room : roomlist) {
            Room roomobj = new Room(room);
            roomList.add(roomService.save(roomobj));
        }
        List<String> hourlist = new ArrayList<String>(Arrays.asList(hours.split(" (?=[a-zA-Z]+\\s[0-9]+\\s-\\s[0-9])")));
        for (String hour : hourlist) {
            if (hour == ""){
                CourseHour courseHour = new CourseHour("", 0, 0);
                courseHourList.add(courseHourService.save(courseHour));
            } else {
            String[] splited = hour.split(" ");
            String day = splited[0];
            String startHour = splited[1];
            int hourfix = Integer.parseInt(splited[3])-1;
            String endHour = hourfix + "";
            CourseHour courseHour = new CourseHour(day, Integer.parseInt(startHour), Integer.parseInt(endHour));
            courseHourList.add(courseHourService.save(courseHour));
            }
        }
        section.setCourseHours(courseHourList);
        section.setRoom(roomList);
    }
}
