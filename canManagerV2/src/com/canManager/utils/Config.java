package com.canManager.utils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javafx.scene.control.Alert;

public class Config {
     
    private static Path pathCatalogDirectory;
    private static Properties prop = new Properties();
    
    public static void iniConfig(){
        try {            
            prop.load(new FileInputStream("resources/config.properties"));
            setCatalogDirectory(prop.getProperty("catalogDirectory"));
            Log.msg(0, "iniConfig");
            
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur - fichier de config");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier de configuration n'a pas pu être chargé.");
            alert.showAndWait();
            System.exit(1);
        }   
    }
    
    public static Path getCatalogDirectory(){
        return pathCatalogDirectory;
    }
    
    public static boolean setCatalogDirectory(String str){
        if(Files.exists(Paths.get(str)))
        {
            if(!prop.getProperty("catalogDirectory").equals(str))
            {
                prop.setProperty("catalogDirectory", str);
                try{
                    prop.store(new FileOutputStream("resources/config.properties"), "edit catalogDirectory");
                    pathCatalogDirectory = Paths.get(str);
                }catch (Exception ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Erreur fatale - fichier de config");
                    alert.setHeaderText(null);
                    alert.setContentText("Le fichier de configuration n'a pas pu être sauvé.");
                    alert.showAndWait();
                    System.exit(1);
                }  
            }
            else
                pathCatalogDirectory = Paths.get(str);
        }
        else{
            str = System.getProperty("user.home") + System.getProperty("file.separator") + "Desktop" + System.getProperty("file.separator");
            prop.setProperty("catalogDirectory", str);
            pathCatalogDirectory = Paths.get(str);
        }
        
        return true;
    }
}
