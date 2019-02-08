/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import javafx.scene.control.TableView;
import tam.jtps.jTPS_Transaction;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.TAData;
import tam.data.TeachingAssistant;
import tam.workspace.TAWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAReplaceUR implements jTPS_Transaction{
    private String TAname;
    private String TAemail;
    private String newName;
    private String newEmail;
    private TAManagerApp app;
    private TAData data;
    private boolean isUndergrad;
    
    public TAReplaceUR(TAManagerApp app){
        this.app = app;
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        data = ((AllData) app.getDataComponent()).getTAData();
        newName = workspace.getNameTextField().getText();
        newEmail = workspace.getEmailTextField().getText();
        TableView taTable = workspace.getTATable();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        TAname = ta.getName();
        TAemail = ta.getEmail();
        isUndergrad = ta.isUndergrad();
    }

    @Override
    public void doTransaction() {
        data.replaceTAName(TAname, newName);
        data.removeTA(TAname);
        if (newName.equals("") || newName == null){
            data.addTA(TAname, newEmail, isUndergrad);
        }
        else if (newEmail.equals("") || newEmail == null){
            data.addTA(newName, TAemail, isUndergrad);
        }
        else{
            data.addTA(newName, newEmail, isUndergrad);
        }
        for (TeachingAssistant ta : data.getTeachingAssistants()){
            System.out.println(ta.getName() + ": " +ta.isUndergrad());
        }
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(newName));
    }

    @Override
    public void undoTransaction() {
        data.replaceTAName(newName, TAname);
        data.removeTA(newName);
        data.addTA(TAname, TAemail, isUndergrad);
        TAWorkspace workspace = (TAWorkspace)app.getWorkspaceComponent();
        TableView taTable = workspace.getTATable();
        taTable.getSelectionModel().select(data.getTA(TAname));
    }
    
    
    
}
