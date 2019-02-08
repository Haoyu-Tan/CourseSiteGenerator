/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import java.util.ArrayList;
import javafx.scene.control.ComboBox;
import tam.jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.TAData;
import tam.file.TimeSlot;
import tam.workspace.TAWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAhourschangeUR implements jTPS_Transaction{
    
    private TAManagerApp app;
    private int startTime;
    private int endTime;
    private int newStartTime;
    private int newEndTime;
    private ArrayList<TimeSlot> officeHours;
    
    public TAhourschangeUR(TAManagerApp app){
        this.app = app;
        TAData data = ((AllData) app.getDataComponent()).getTAData();
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ComboBox comboBox1 = workspace.getOfficeHour(true);
        ComboBox comboBox2 = workspace.getOfficeHour(false);
        startTime = data.getStartHour();
        endTime = data.getEndHour();
        newStartTime = comboBox1.getSelectionModel().getSelectedIndex();
        newEndTime = comboBox2.getSelectionModel().getSelectedIndex();
        officeHours = TimeSlot.buildOfficeHoursList(data);
    }

    @Override
    public void doTransaction() {
        ((TAWorkspace)app.getWorkspaceComponent()).getOfficeHoursGridPane().getChildren().clear();
        ((AllData) app.getDataComponent()).getTAData().changeTime(newStartTime, newEndTime, officeHours);
    }

    @Override
    public void undoTransaction() {
        ((TAWorkspace)app.getWorkspaceComponent()).getOfficeHoursGridPane().getChildren().clear();
        ((AllData) app.getDataComponent()).getTAData().changeTime(startTime, endTime, officeHours);
    }
    
}
