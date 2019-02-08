/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.jtps;

import tam.data.TeachingAssistant;

/**
 *
 * @author Suriri
 */
public class TAUndergradUR implements jTPS_Transaction{
    TeachingAssistant ta;
    boolean newTran;
    public TAUndergradUR(TeachingAssistant ta){
        this.ta = ta;
        newTran = true;
    }

    @Override
    public void doTransaction() {
        if (!newTran){
            if (ta.isUndergrad()){
                ta.setUndergrad(false);
            }
            else{
                ta.setUndergrad(true);
            }
        }
    }

    @Override
    public void undoTransaction() {
        if (ta.isUndergrad()){
            ta.setUndergrad(false);
        }
        else{
            ta.setUndergrad(true);
        }
        newTran = false;
    }
    
}
