package com.canManager.data;

import com.canManager.utils.Tools;
import java.nio.file.Files;
import javafx.scene.control.Alert;

class DbfBody {

    private int sizeHeader, sizeRecord;
    
    DbfBody(DbfHeader header) {

        //é -> -126
        //à -> -123
        //è -> -118
        //http://www.asciitable.com/
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
}
