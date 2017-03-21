package com.canManager.data;

import com.canManager.utils.Log;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import javafx.scene.control.Alert;

public class ReadSoum {
    private static List<String> file = null;
    
    public static void setFile(String pathSoum){
        ReadSoum.file = null;
        try{
            ReadSoum.file = Files.readAllLines(Paths.get(pathSoum), Charset.forName("IBM437"));
            Log.msg(0, "setFile() - " + pathSoum);
            Log.msg(0, "setFile() - " + ReadSoum.file.size() + " lines");
        }catch(Exception e){
            Log.msg(1, "Le fichier .01s ne peut-être lu.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + pathSoum + "\" ne peut-être lu.\n" + e.getMessage());
            alert.showAndWait();  
            Log.msg(1, "setFile() fail");
        }
    }

    public static List<String> getRawData(){
        if(ReadSoum.file==null)
        {
            Log.msg(1, "getRawData == null");
            return null;
        }
        else{
            return ReadSoum.file;
        }
    }
}


/*  


    
    private static List<String> getFile() {        
        
        return ReadSoum.file;            
    }*/