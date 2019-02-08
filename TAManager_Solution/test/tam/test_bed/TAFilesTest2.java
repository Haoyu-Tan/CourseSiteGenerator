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
public class TAFilesTest2 {
    static AllData allData;
    static CourseSiteData courseSiteData;
    static RecitationData recitationData;
    static ScheduleData scheduleData;
    static ProjectData projectData;
    static TAData taData;
    public TAFilesTest2() {
        
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
        
        
    }

    
}
