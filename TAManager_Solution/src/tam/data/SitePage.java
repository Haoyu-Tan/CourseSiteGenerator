/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Suriri
 */
public class SitePage {
    String navbar;
    String fileName;
    String script;
    BooleanProperty use;
    public SitePage(String nav, String fileName, String script, boolean isUse){
        this.navbar = nav;
        this.fileName = fileName;
        this.script = script;
        use = new SimpleBooleanProperty(isUse);
    }
    public String getNavbar(){
        return navbar;
    }
    
    public String getFileName(){
        return fileName;
    }
    public String getScript(){
        return script;
    }
    
    public void setNavbar(String newNavbar){
        navbar = newNavbar;
    }
    
    public void setFileName(String newFileName){
        fileName = newFileName;
    }
    
    public void setScript(String newScript){
        script = newScript;
    }
    
    public BooleanProperty useProperty(){
        return use;
    }
    
    public boolean isUsed(){
        return this.use.get();
    }
    
    public void setUse(boolean value){
        this.use.set(value);
    }
}
