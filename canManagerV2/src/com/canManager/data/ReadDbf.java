package com.canManager.data;

import com.canManager.utils.Log;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class ReadDbf {

    private static Path dbfFile = null;
    private static ArrayList<Articles> listArticles = new ArrayList<>();
    
    public static List<Articles> getListArticles(){
        Log.msg(0, "listArticles");
        if(listArticles.isEmpty())
            importFromDbf();

        return listArticles;
    }
    
    private static void importFromDbf()
    {
        DbfHeader header = new DbfHeader(dbfFile);   
        try {
            byte[] data = Files.readAllBytes(header.getPathDbfFile());

            int tmp=header.getNumHeader();

            for(int j=1; j<header.getNumRecordsTable(); j++)
            {
                for(int i=tmp; i<(header.getNumRecord()+tmp); i++){
                    if(data[i]<0)
                        data[i]=(byte)(data[i]+256);
                    System.out.print((char)data[i]);
                }
                System.out.print(" | ");
                for(int i=tmp; i<(header.getNumRecord()+tmp); i++){
                    if(data[i]<0)
                        data[i]+=256;                    
                    System.out.print(data[i] + "|");
                }
                
                System.out.println();
                tmp+=header.getNumRecord();
            }
            char character = 'é';
            System.out.println((int)character);
            
        } catch(Exception e){
            System.out.println("[ X ] " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DbfBody");
            alert.setHeaderText(null);
            alert.setContentText(header.getPathDbfFile().toString() + "\nErreur lors de l'ouverture du fichier\nVeuillez choisir un autre nom de fichier et réessayer.\nErreur : " + e.getMessage());
            alert.showAndWait();       
        }         
    }
    
    public static boolean setDbfFile(String str){
        dbfFile = Paths.get(str);
        if(Files.exists(dbfFile))
        {
            Log.msg(0, "Le fichier dbf existe.");
            return true;
        }
        else
        {
            Log.msg(1, "Le fichier dbf n'existe pas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Convertir");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + dbfFile.toString() + "\" n'existe pas.");
            alert.showAndWait();
            return false;
        }
    }
}
