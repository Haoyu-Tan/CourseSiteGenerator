/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ScheduleData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class ScheduleUpdateUR implements jTPS_Transaction{
    String newType;
    String newDate;
    String newTime;
    String newTitle;
    String newTopic;
    String newLink;
    String newCriteria;
    
    String oldType;
    String oldDate;
    String oldTime;
    String oldTitle;
    String oldTopic;
    String oldLink;
    String oldCriteria;
    
    TAManagerApp app;
    ScheduleData scheduleData;
    
    public ScheduleUpdateUR(String nType, String nDate, String nTime, String nTitle, String nTopic,
            String nLink, String nCriteria, String oType, String oDate, String oTime, String oTitle,
            String oTopic, String oLink, String oCriteria, TAManagerApp initApp){
        newType = nType;
        newDate = nDate;
        newTime = nTime;
        newTitle = nTitle;
        newTopic = nTopic;
        newLink = nLink;
        newCriteria = nCriteria;
        
        oldType = oType;
        oldDate = oDate;
        oldTime = oTime;
        oldTitle = oTitle;
        oldTopic = oTopic;
        oldLink = oLink;
        oldCriteria = oCriteria;
        
        app = initApp;
        scheduleData = ((AllData)app.getDataComponent()).getScheduleData();
    }

    @Override
    public void doTransaction() {
        scheduleData.removeSchedule(oldType, oldDate, oldTime, oldTitle, oldTopic, oldLink, oldCriteria);
        scheduleData.addSchedule(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.getScheduleTable().getSelectionModel().select(scheduleData.getSchedule(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria));
    }

    @Override
    public void undoTransaction() {
        scheduleData.removeSchedule(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
        scheduleData.addSchedule(oldType, oldDate, oldTime, oldTitle, oldTopic, oldLink, oldCriteria);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        workspace.getScheduleTable().getSelectionModel().select(scheduleData.getSchedule(oldType, oldDate, oldTime, oldTitle, oldTopic, oldLink, oldCriteria));
    }
    
}
