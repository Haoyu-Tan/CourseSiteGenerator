/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import djf.components.AppDataComponent;
import tam.TAManagerApp;

/**
 *
 * @author Suriri
 */
public class AllData implements AppDataComponent{
     TAData TAData;
     CourseSiteData courseSiteData;
     RecitationData recitationData;
     ScheduleData scheduleData;
     ProjectData projectData;
     TAManagerApp app;
    public AllData(TAManagerApp initApp) {
        //System.out.println("yeh");
        app = initApp;
        TAData = new TAData(app);
        courseSiteData = new CourseSiteData(app);
        recitationData = new RecitationData(app);
        scheduleData = new ScheduleData(app);
        projectData = new ProjectData(app);
    }
    
    public AllData(){
        TAData = new TAData();
        courseSiteData = new CourseSiteData();
        recitationData = new RecitationData();
        scheduleData = new ScheduleData();
        projectData = new ProjectData();
    }

    @Override
    public void resetData() {
        TAData.resetData();
        courseSiteData.resetData();
        recitationData.resetData();
        scheduleData.resetData();
        projectData.resetData();
        
    }
    
    public TAData getTAData(){
        return TAData;
    }
    
    public CourseSiteData getCourseSiteData(){
        return courseSiteData;
    }
    
    public RecitationData getRecitationData(){
        return recitationData;
    }
    
    public ScheduleData getScheduleData(){
        return scheduleData;
    }
    
    public ProjectData getProjectData(){
        return projectData;
    }
}

