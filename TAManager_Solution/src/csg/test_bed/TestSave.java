/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;
//import djf.AppTemplate;
import djf.components.AppDataComponent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.Label;
import tam.TAManagerApp;
import tam.data.*;
import tam.file.TAFiles;
import tam.file.TimeSlot;
import javafx.scene.control.Tooltip;

/**
 *
 * @author Suriri
 */
public class TestSave {
    public static void main(String[] args) throws IOException{
        AllData allData = new AllData();
        CourseSiteData courseSiteData = allData.getCourseSiteData();
        TAData taData = allData.getTAData();
        ProjectData projectData = allData.getProjectData();
        RecitationData recitationData = allData.getRecitationData();
        ScheduleData scheduleData = allData.getScheduleData();
        
        courseSiteData.setSubject("CSE");
        courseSiteData.setNumber("219");
        courseSiteData.setSemester("Spring");
        courseSiteData.setYear("2017");
        courseSiteData.setExportDir("/Users/Suri/Documents/cse219/Haoyu Tan HW5/CourseSiteGenerator.json");
        courseSiteData.setTitle("Computer Science III");
        courseSiteData.setInstructorName("Richard McKenna");
        courseSiteData.setInstructorHome("http://www.cs.stonybrook.edu/~richard");
        courseSiteData.setTemplateDir("TEMPLATE DIRECTORY");
        courseSiteData.setSchoolImage("file:./images/drink.png");
        courseSiteData.setLeftImage("file:./images/pizza.png");
        courseSiteData.setRightImage("file:./images/cake.png");
        courseSiteData.setStyleSheet("style_sheet.css");
        
        SitePage page1 = new SitePage("Home", "index.html", "HomeBuilder.js", true);
        SitePage page2 = new SitePage("Syllabus", "syllabus.html", "SyllabusBuilder.js", true);
        SitePage page3 = new SitePage("Schedule", "schedule.html", "ScheduleBuilder.js", true);
        SitePage page4 = new SitePage("HWs", "hws.html", "HWsBuilder.js", true);
        SitePage page5 = new SitePage("Projects", "projects.html", "ProjectsBuilder.js", true);
        
        ObservableList<SitePage> site = courseSiteData.getSitePage();
        site.add(page1);
        site.add(page2);
        site.add(page3);
        site.add(page4);
        site.add(page5);
        
        TeachingAssistant a = new TeachingAssistant("a", "a@abc.com", true);
        TeachingAssistant b = new TeachingAssistant("b", "b@abc.com", true);
        TeachingAssistant c = new TeachingAssistant("c", "c@abc.com", true);
        TeachingAssistant d = new TeachingAssistant("d", "d@abc.com", false);
        TeachingAssistant e = new TeachingAssistant("e", "e@abc.com", false);
        TeachingAssistant f = new TeachingAssistant("f", "f@abc.com", false);
        //TeachingAssistant g = new TeachingAssistant("g", "g@abc.com", false);
        
        taData.setStartHour(5);
        taData.setEndHour(20);
        
        /**
        Label cellLabel1 = new Label("a");
        Label cellLabel2 = new Label("b");
        Label cellLabel3 = new Label("c");
        Label cellLabel4 = new Label("d");
        Label cellLabel5 = new Label("e");
        Label cellLabel6 = new Label("f");
        
        cellLabel1.setId("2_3");
        cellLabel2.setId("10_14");
        cellLabel3.setId("11_8");
        cellLabel4.setId("7_7");
        cellLabel5.setId("12_5");
        cellLabel6.setId("7_11");
        
        taData.setCellProperty(2, 3, cellLabel1.textProperty());
        taData.setCellProperty(10, 14, cellLabel2.textProperty());
        taData.setCellProperty(11, 8, cellLabel3.textProperty());
        taData.setCellProperty(7, 7, cellLabel4.textProperty());
        taData.setCellProperty(12, 5, cellLabel5.textProperty());
        taData.setCellProperty(7, 11, cellLabel6.textProperty());
        * 
        **/ 
        HashMap<String, StringProperty> officeHours = taData.getOfficeHours();
            for (int row = 1; row < 49; row++) {
                for (int col = 2; col < 7; col++) {
                    officeHours.put(col + "_" + row, new SimpleStringProperty(""));
                }
        }
        
        for (int row = 1; row <= 24; row++){
            if ((double)row % 2 != 0){
                int newRow = row / 2;
                officeHours.put(0 + "_" + row, new SimpleStringProperty(newRow + ":00am"));
                System.out.println(newRow);
            }
            else{
                int newRow = row / 2 - 1;
                officeHours.put(0 + "_" + row, new SimpleStringProperty(newRow + ":30am"));
                System.out.println(newRow);
            }
        }
        
        officeHours.put(0 + "_" + 25, new SimpleStringProperty("12:00pm"));
        officeHours.put(0 + "_" + 26, new SimpleStringProperty("12:30pm"));
        
        for (int row = 27; row <= 48; row++){
            if ((double)row % 2 != 0){
                int newRow = row / 2 - 12;
                officeHours.put(0 + "_" + row, new SimpleStringProperty(newRow + ":00pm"));
                System.out.println(newRow);
            }
            else{
                int newRow = row / 2 - 13;
                officeHours.put(0 + "_" + row, new SimpleStringProperty(newRow + ":30pm"));
                System.out.println(newRow);
            }
        }
        
        
        
        StringProperty prop1 = new SimpleStringProperty("a");
        StringProperty prop2 = new SimpleStringProperty("b");
        StringProperty prop3 = new SimpleStringProperty("c");
        StringProperty prop4 = new SimpleStringProperty("c\ne");
        StringProperty prop5 = new SimpleStringProperty("a");
        StringProperty prop6 = new SimpleStringProperty("d\nc");
        
        taData.setCellProperty(2, 28, prop1);
        taData.setCellProperty(5, 26, prop2);
        taData.setCellProperty(4, 19, prop3);
        taData.setCellProperty(3, 25, prop4);
        taData.setCellProperty(3, 30, prop5);
        taData.setCellProperty(6, 14, prop6);
        
       
        
        
        ObservableList<TeachingAssistant> ta = taData.getTeachingAssistants();
        ta.add(a);
        ta.add(b);
        ta.add(c);
        ta.add(d);
        ta.add(e);
        ta.add(f);
        //ta.add(g);
        
        Recitation rec1 = new Recitation("01", "ta1", "1/1/2017 12:00pm", "New Computer Science 2203", "a", "b");
        Recitation rec2 = new Recitation("02", "ta2", "1/1/2017 3:00pm", "Javit", "c", "d");
        Recitation rec3 = new Recitation("03", "ta3", "1/1/2017 8:00pm", "Frey Hall", "e", "f");
        Recitation rec4 = new Recitation("04", "ta4", "1/1/2017 4:00pm", "SAC BALL ROOM A", "g", "h");
        
        ObservableList<Recitation> recitation = recitationData.getRecitation();
        recitation.add(rec1);
        recitation.add(rec2);
        recitation.add(rec3);
        recitation.add(rec4);
        
        Schedule schedule1 = new Schedule("hw", "1/1/2017", "12:00pm", "HW1", "HW", "NO", "NONE");
        Schedule schedule2 = new Schedule("lecture", "1/2/2017", "8:00pm", "Lecture2", "Lecture", "NO", "NONE");
        Schedule schedule3 = new Schedule("hw", "1/4/2017", "3:00pm", "HW2", "HW", "NO", "NONE");
        
        ObservableList<Schedule> schedule = scheduleData.getSchedule();
        schedule.add(schedule1);
        schedule.add(schedule2);
        schedule.add(schedule3);
        
        scheduleData.setEndDate("10/10/2017");
        scheduleData.setStartDate("1/1/2017");
        
        Team teamA = new Team("Aqua", "0x0000ffff", "0x88ff0102", "None");
        Team teamB = new Team("Aquamarine", "0x777fffd4", "0x66abdd01", "None");
        Team teamC = new Team("Battery Charge Blue", "0x6667c8ff", "0x22222222", "None");
        
        ObservableList<Team> team = projectData.getTeam();
        team.add(teamA);
        team.add(teamB);
        team.add(teamC);
        
        Student studentA = new Student("suri", "T", "Aqua", "Lead Programmer");
        Student studentB = new Student("sophia", "S", "Aquamarine", "Data Designer");
        Student studentC = new Student("sunny", "L", "Aqua", "Lead Designer");
        Student studentD = new Student("eden", "Z", "Battery Charge Blue", "Lead Designer");
        Student studentE = new Student("", "", "Aqua", "Project Manager");
        Student studentF = new Student("", "", "Aqua", "Data Designer");
        Student studentG = new Student("", "", "Aquamarine", "Lead Programmer");
        Student studentH = new Student("", "", "Aquamarine", "Project Manager");
        Student studentI = new Student("", "", "Aquamarine", "Lead Designer");
        Student studentJ = new Student("", "", "Battery Charge Blue", "Lead Programmer");
        Student studentK = new Student("", "", "Battery Charge Blue", "Project Manager");
        Student studentL = new Student("", "", "Battery Charge Blue", "Data Designer");
        
        
        
        
        
        ObservableList<Student> student = projectData.getStudent();
        student.add(studentA);
        student.add(studentB);
        student.add(studentC);
        student.add(studentD);
        student.add(studentE);
        student.add(studentF);
        student.add(studentG);
        student.add(studentH);
        student.add(studentI);
        student.add(studentJ);
        student.add(studentK);
        student.add(studentL);
        
        
        AppDataComponent appData = (AppDataComponent)allData;
        TAFiles file = new TAFiles();
        file.saveData(appData, "./work/SiteSaveTest2.json");
        
        for (String str : officeHours.keySet()){
            System.out.println("key is " + str + " value is " + officeHours.get(str));
        }
        
    }
}
