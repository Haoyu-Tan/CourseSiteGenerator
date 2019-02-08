/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.TextField;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import static tam.TAManagerProp.SAME_RECITATION_TEXT;
import static tam.TAManagerProp.SAME_RECITATION_TITLE;
import static tam.TAManagerProp.SAME_SCHEDULE_TEXT;
import static tam.TAManagerProp.SAME_SCHEDULE_TITLE;
import tam.data.AllData;
import tam.data.ScheduleData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class ScheduleAddUR implements jTPS_Transaction{
    String newType;
    String newDate;
    String newTime;
    String newTitle;
    String newTopic;
    String newLink;
    String newCriteria;
    ScheduleData scheduleData;
    TAManagerApp app;
    
    public ScheduleAddUR(String nType, String nDate, String nTime, String nTitle, String nTopic,
            String nLink, String nCriteria, TAManagerApp initApp){
        newType = nType;
        newDate = nDate;
        newTime = nTime;
        newTitle = nTitle;
        newTopic = nTopic;
        newLink = nLink;
        newCriteria = nCriteria;
        
        app = initApp;
        scheduleData = ((AllData)app.getDataComponent()).getScheduleData();
        
    }
    

    @Override
    public void doTransaction() {
        if (!scheduleData.isContains(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria)){
            scheduleData.addSchedule(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
            TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
            workspace.getTimeText().clear();
            workspace.getScheduleTitleText().clear();
            workspace.getTopicText().clear();
            workspace.getLinkText().clear();
            workspace.getCriteriaText().clear();
            workspace.getScheduleTypeCombo().getSelectionModel().select(null);
            workspace.getScheduleDate().setValue(null);
        }
        else{
            PropertiesManager props = PropertiesManager.getPropertiesManager();
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(SAME_SCHEDULE_TITLE), props.getProperty(SAME_SCHEDULE_TEXT));
        }
    }

    @Override
    public void undoTransaction() {
        scheduleData.removeSchedule(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
    }
    
}
