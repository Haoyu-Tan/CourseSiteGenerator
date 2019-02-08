/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.test_bed;

import djf.components.AppDataComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import tam.file.TAFiles;
import static org.junit.Assert.*;
import tam.data.AllData;
import tam.data.CourseSiteData;
import tam.data.ProjectData;
import tam.data.Recitation;
import tam.data.RecitationData;
import tam.data.Schedule;
import tam.data.ScheduleData;
import tam.data.SitePage;
import tam.data.Student;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.data.Team;
import tam.file.TAFiles;
import tam.file.TAFiles;

/**
 *
 * @author Suriri
 */
public class TAFilesTest {
    static AllData allData;
    static CourseSiteData courseSiteData;
    static RecitationData recitationData;
    static ScheduleData scheduleData;
    static ProjectData projectData;
    static TAData taData;
    public TAFilesTest() {
        
    }
    
    @BeforeClass
    public static void setUpClass() throws IOException {
        allData = new AllData();
        AppDataComponent dataComponent = (AppDataComponent)allData;
        String filePath = "./work/SiteSaveTest.json";
        TAFiles taFile = new TAFiles();
        taFile.loadData(dataComponent, filePath);
        
        courseSiteData = allData.getCourseSiteData();
        recitationData = allData.getRecitationData();
        scheduleData = allData.getScheduleData();
        projectData = allData.getProjectData();
        taData = allData.getTAData();
        
        
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of loadData method, of class TAFiles.
     */
    @Test
    public void testLoadData() throws Exception {
        System.out.println("* loadData()");
        
        //Test for the course site pane
        System.out.println("* COURSE SITE DATA");
        assertEquals("CSE", courseSiteData.getSubject());
        assertEquals("219", courseSiteData.getNumber());
        assertEquals("Spring", courseSiteData.getSemester());
        assertEquals("2017", courseSiteData.getYear());
        assertEquals("Computer Science III", courseSiteData.getTitle());
        assertEquals("Richard McKenna", courseSiteData.getInstructorName());
        assertEquals("http://www.cs.stonybrook.edu/~richard", courseSiteData.getInstructorHome());
        assertEquals("TEMPLATE DIRECTORY", courseSiteData.getTemplateDir());
        assertEquals("/Users/Suri/Documents/cse219/Haoyu Tan HW5/CourseSiteGenerator.json", 
                courseSiteData.getExportDir());
        //Test Site Page
        System.out.println("* Site Page");
        ObservableList<SitePage> siteList = courseSiteData.getSitePage();
        assertEquals(5, siteList.size());
        
        SitePage sp0 = siteList.get(0);
        assertEquals("Home", sp0.getNavbar());
        assertEquals("index.html", sp0.getFileName());
        assertEquals("HomeBuilder.js", sp0.getScript());
        assertTrue(sp0.isUsed());
        
        SitePage sp1 = siteList.get(1);
        assertEquals("Syllabus", sp1.getNavbar());
        assertEquals("syllabus.html", sp1.getFileName());
        assertEquals("SyllabusBuilder.js", sp1.getScript());
        assertTrue(sp1.isUsed());
        
        SitePage sp2 = siteList.get(2);
        assertEquals("Schedule", sp2.getNavbar());
        assertEquals("schedule.html", sp2.getFileName());
        assertEquals("ScheduleBuilder.js", sp2.getScript());
        assertTrue(sp2.isUsed());
        
        SitePage sp3 = siteList.get(3);
        assertEquals("HWs", sp3.getNavbar());
        assertEquals("hws.html", sp3.getFileName());
        assertEquals("HWsBuilder.js", sp3.getScript());
        assertTrue(sp3.isUsed());
        
        SitePage sp4 = siteList.get(4);
        assertEquals("Projects", sp4.getNavbar());
        assertEquals("projects.html", sp4.getFileName());
        assertEquals("ProjectsBuilder.js", sp4.getScript());
        assertTrue(sp4.isUsed());
        
        assertEquals("file:./images/drink.png", courseSiteData.getSchoolImage());
        assertEquals("file:./images/pizza.png", courseSiteData.getLeftImage());
        assertEquals("file:./images/cake.png", courseSiteData.getRightImage());
        assertEquals("style_sheet.css", courseSiteData.getStyleSheet());
        
        //TA DATA
        assertEquals(5, taData.getStartHour());
        assertEquals(20, taData.getEndHour());
        
        ObservableList<TeachingAssistant> teachingAssistant = taData.getTeachingAssistants();
        
        assertEquals(6, teachingAssistant.size());
        
        TeachingAssistant ta0 = teachingAssistant.get(0);
        assertEquals("a", ta0.getName());
        assertEquals("a@abc.com", ta0.getEmail());
        assertTrue(ta0.isUndergrad());
        
        TeachingAssistant ta1 = teachingAssistant.get(1);
        assertEquals("b", ta1.getName());
        assertEquals("b@abc.com", ta1.getEmail());
        assertTrue(ta1.isUndergrad());
        
        TeachingAssistant ta2 = teachingAssistant.get(2);
        assertEquals("c", ta2.getName());
        assertEquals("c@abc.com", ta2.getEmail());
        assertTrue(ta2.isUndergrad());
        
        TeachingAssistant ta3 = teachingAssistant.get(3);
        assertEquals("d", ta3.getName());
        assertEquals("d@abc.com", ta3.getEmail());
        assertFalse(ta3.isUndergrad());
        
        TeachingAssistant ta4 = teachingAssistant.get(4);
        assertEquals("e", ta4.getName());
        assertEquals("e@abc.com", ta4.getEmail());
        assertFalse(ta4.isUndergrad());
        
        TeachingAssistant ta5 = teachingAssistant.get(5);
        assertEquals("f", ta5.getName());
        assertEquals("f@abc.com", ta5.getEmail());
        assertFalse(ta5.isUndergrad());
        
        HashMap<String, StringProperty> officeHours = taData.getOfficeHours();
        
        assertEquals("d\nc", officeHours.get("6_4").get());
        assertEquals("b", officeHours.get("5_16").get());
        assertEquals("c", officeHours.get("4_9").get());
        assertEquals("a", officeHours.get("2_18").get());
        assertEquals("c\ne", officeHours.get("3_15").get());
        assertEquals("a", officeHours.get("3_20").get());
        
        
        
        //test office hour list
        //ArrayList<TimeSlot> timeList = TimeSlot.buildOfficeHoursList(taData);
        //TimeSlot ts = timeList.get(0);
        //System.out.println(ts.getDay());
        
        
        //Recitation Data
        ObservableList<Recitation> recitation = recitationData.getRecitation();
        
        assertEquals(4, recitation.size());
        
        Recitation rec0 = recitation.get(0);
        assertEquals("01", rec0.getSection());
        assertEquals("ta1", rec0.getInstructor());
        assertEquals("1/1/2017 12:00pm", rec0.getDay());
        assertEquals("New Computer Science 2203", rec0.getLocation());
        assertEquals("a", rec0.getTa1());
        assertEquals("b", rec0.getTa2());
        
        Recitation rec1 = recitation.get(1);
        assertEquals("02", rec1.getSection());
        assertEquals("ta2", rec1.getInstructor());
        assertEquals("1/1/2017 3:00pm", rec1.getDay());
        assertEquals("Javit", rec1.getLocation());
        assertEquals("c", rec1.getTa1());
        assertEquals("d", rec1.getTa2());
        
        Recitation rec2 = recitation.get(2);
        assertEquals("03", rec2.getSection());
        assertEquals("ta3", rec2.getInstructor());
        assertEquals("1/1/2017 8:00pm", rec2.getDay());
        assertEquals("Frey Hall", rec2.getLocation());
        assertEquals("e", rec2.getTa1());
        assertEquals("f", rec2.getTa2());
        
        Recitation rec3 = recitation.get(3);
        assertEquals("04", rec3.getSection());
        assertEquals("ta4", rec3.getInstructor());
        assertEquals("1/1/2017 4:00pm", rec3.getDay());
        assertEquals("SAC BALL ROOM A", rec3.getLocation());
        assertEquals("g", rec3.getTa1());
        assertEquals("h", rec3.getTa2());
        
        assertEquals("1/1/2017", scheduleData.getStartDate());
        assertEquals("10/10/2017", scheduleData.getEndDate());
        
        
        //Schedule Data
        ObservableList<Schedule> schedule = scheduleData.getSchedule();
        
        assertEquals(3, schedule.size());
        
        Schedule schedule0 = schedule.get(0);
        assertEquals("hw", schedule0.getType());
        assertEquals("1/1/2017", schedule0.getDate());
        assertEquals("12:00pm", schedule0.getTime());
        assertEquals("HW1", schedule0.getTitle());
        assertEquals("HW", schedule0.getTopic());
        assertEquals("NO", schedule0.getLink());
        assertEquals("NONE", schedule0.getCriteria());
        
        Schedule schedule1 = schedule.get(1);
        assertEquals("lecture", schedule1.getType());
        assertEquals("1/2/2017", schedule1.getDate());
        assertEquals("8:00pm", schedule1.getTime());
        assertEquals("Lecture2", schedule1.getTitle());
        assertEquals("Lecture", schedule1.getTopic());
        assertEquals("NO", schedule1.getLink());
        assertEquals("NONE", schedule1.getCriteria());
        
        Schedule schedule2 = schedule.get(2);
        assertEquals("hw", schedule2.getType());
        assertEquals("1/4/2017", schedule2.getDate());
        assertEquals("3:00pm", schedule2.getTime());
        assertEquals("HW2", schedule2.getTitle());
        assertEquals("HW", schedule2.getTopic());
        assertEquals("NO", schedule2.getLink());
        assertEquals("NONE", schedule2.getCriteria());
        
        
        //Project Data
        ObservableList<Team> teamList = projectData.getTeam();
        
        assertEquals(3, teamList.size());
        
        Team team0 = teamList.get(0);
        assertEquals("Aqua", team0.getTeamName());
        assertEquals("#00ffff", team0.getColor());
        assertEquals("#ff0102", team0.getTextColor());
        assertEquals("None", team0.getLink());
        
        Team team1 = teamList.get(1);
        assertEquals("Aquamarine", team1.getTeamName());
        assertEquals("#7fffd4", team1.getColor());
        assertEquals("#abdd01", team1.getTextColor());
        assertEquals("None", team1.getLink());
        
        Team team2 = teamList.get(2);
        assertEquals("Battery Charge Blue", team2.getTeamName());
        assertEquals("#67c8ff", team2.getColor());
        assertEquals("#222222", team2.getTextColor());
        assertEquals("None", team2.getLink());
        
        
        //Student Data
        ObservableList<Student> studentList = projectData.getStudent();
        
        System.out.println(studentList.size());
        assertEquals(12, studentList.size());
        
        Student student0 = studentList.get(0);
        assertEquals("suri", student0.getFirstName());
        assertEquals("T", student0.getLastName());
        assertEquals("Aqua", student0.getTeam());
        assertEquals("Lead Programmer", student0.getRole());
        
        Student student1 = studentList.get(1);
        assertEquals("sophia", student1.getFirstName());
        assertEquals("S", student1.getLastName());
        assertEquals("Aquamarine", student1.getTeam());
        assertEquals("Data Designer", student1.getRole());
        
        Student student2 = studentList.get(2);
        assertEquals("sunny", student2.getFirstName());
        assertEquals("L", student2.getLastName());
        assertEquals("Aqua", student2.getTeam());
        assertEquals("Lead Designer", student2.getRole());
        
        Student student3 = studentList.get(3);
        assertEquals("eden", student3.getFirstName());
        assertEquals("Z", student3.getLastName());
        assertEquals("Battery Charge Blue", student3.getTeam());
        assertEquals("Lead Designer", student3.getRole());
        //AppDataComponent data = null;
        //String filePath = "";
        //TAFiles instance = new TAFiles();
        //instance.loadData(data, filePath);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    
}
