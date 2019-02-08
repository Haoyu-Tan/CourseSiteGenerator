/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import java.util.regex.Pattern;
import tam.jtps.jTPS_Transaction;
import tam.TAManagerApp;
import tam.data.AllData;
import tam.data.TAData;
import tam.workspace.TAController;
import tam.workspace.TAWorkspace;

/**
 *
 * @author zhaotingyi
 */
public class TAAdderUR implements jTPS_Transaction{
    
    private String TAName;
    private String TAEmail;
    private TAManagerApp app;
    private TAWorkspace workspace;
    
    public TAAdderUR(TAManagerApp app){
        this.app = app;
        workspace = (TAWorkspace)app.getWorkspaceComponent();
        TAName = workspace.getNameTextField().getText();
        TAEmail = workspace.getEmailTextField().getText();
    }

    @Override
    public void doTransaction() {
        ((AllData) app.getDataComponent()).getTAData().addTA(TAName, TAEmail, false);
    }

    @Override
    public void undoTransaction() {
        ((AllData) app.getDataComponent()).getTAData().removeTA(TAName);
    }
    
}
