/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import java.time.LocalDate;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.ScheduleData;
import tam.workspace.TAWorkspace;

/**
 *
 * @author Suriri
 */
public class ScheduleStartDateUR implements jTPS_Transaction{
    String newStartDate;
    String oldStartDate;
    String newEndDate;
    String oldEndDate;
    TAManagerApp app;
    ScheduleData scheduleData;
    public static boolean isUndo = false;
    public static boolean isRedo = false;
    
    public ScheduleStartDateUR(String nStart, String oStart, String nEnd, String oEnd, TAManagerApp initApp){
        newStartDate = nStart;
        oldStartDate = oStart;
        newEndDate = nEnd;
        oldEndDate = oEnd;
        app = initApp;
        scheduleData = ((AllData)app.getDataComponent()).getScheduleData();
    }

    @Override
    public void doTransaction() {
        scheduleData.setStartDate(newStartDate);
        scheduleData.setEndDate(newEndDate);
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        String[] newS = newStartDate.split("/");
        int [] intS = new int[3];
        
        for (int i = 0; i < 3; i++){
            intS[i] = Integer.parseInt(newS[i]);
        }
        
        //String[] newE = newEndDate.split("/");
        //int[] intE = new int[3];
        
        //for(int i = 0; i < 3; i++){
          //  intE[i] = Integer.parseInt(newE[i]);
        //}
        
        workspace.getScheduleStart().setValue(LocalDate.of(intS[2], intS[0], intS[1]));
        //ScheduleEndDateUR.isRedo = true;
        //workspace.getScheduleEnd().setValue(LocalDate.of(intE[2], intE[0], intE[1]));
        
        
    }

    @Override
    public void undoTransaction() {
        
        scheduleData.setStartDate(oldStartDate);
        TAWorkspace workspace = (TAWorkspace) app.getWorkspaceComponent();
        String[] oldS = oldStartDate.split("/");
        int [] intS = new int[3];
        
        for (int i = 0; i < 3; i++){
            intS[i] = Integer.parseInt(oldS[i]);
        }
        isUndo = true;
        workspace.getScheduleStart().setValue(LocalDate.of(intS[2], intS[0], intS[1]));
    }
    
}
