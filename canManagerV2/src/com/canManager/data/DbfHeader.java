package com.canManager.data;

import java.nio.file.Files;
import java.nio.file.Path;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DbfHeader {

    private int version, dateY, dateM, dateD, numRecordsTable, numHeader, numRecord;
    private Path dbfFile;
        
    public DbfHeader(Path dbfFile){
        
        try {
            byte[] data = Files.readAllBytes(dbfFile);
            this.dbfFile=dbfFile;
            
            setVersion(data[0]);
            setDateY(data[1]);
            setDateM(data[2]);
            setDateD(data[3]);
            
            setNumRecordsTable(byteToIntLsb(data[4], data[5], data[6], data[7]));
            setNumHeader(byteToIntLsb(data[8], data[9]));
            setNumRecord(byteToIntLsb(data[10], data[11]));

        } catch(Exception e){
            System.out.println("[ X ] " + e.getMessage());
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("DbfHeader");
            alert.setHeaderText(null);
            alert.setContentText(dbfFile.toString() + "\nErreur lors de l'ouverture du fichier\nVeuillez choisir un autre nom de fichier et réessayer.\nErreur : " + e.getMessage());
            alert.showAndWait();        
        }
    }
    
    //Version
    private void setVersion(byte b) {
        this.version = b;
    }
    public int getVersion(){
        return this.version;
    }

    //dateY
    private void setDateY(byte b) {
        this.dateY = b+1900;
    }
    public int getDateY(){
        return this.dateY;
    }

    //dateM
    private void setDateM(byte b) {
        this.dateM = b;
    }
    public int getDateM(){
        return this.dateM;
    }

    //dateD
    private void setDateD(byte b) {
        this.dateD = b;
    }
    public int getDateD(){
        return this.dateD;
    }

    //numRecordsTable
    private void setNumRecordsTable(int i) {
        this.numRecordsTable = i;
    }
    public int getNumRecordsTable(){
        return this.numRecordsTable;
    }
    
    //numHeader
    private void setNumHeader(int i) {
        this.numHeader = i;
    }
    public int getNumHeader(){
        return this.numHeader;
    }

    //numRecord
    private void setNumRecord(int i) {
        this.numRecord = i;
    }    
    public int getNumRecord(){
        return this.numRecord;
    }
    
    //dbfFile
    public Path getPathDbfFile(){
        return this.dbfFile;
    }
    
    private int byteToIntLsb(byte firstByte, byte secondByte) {
        int result = (secondByte << 8) + firstByte;
        
        return result;
    }

    private int byteToIntLsb(byte firstByte, byte secondByte, byte thirdByte, byte fourthByte){
        int result = (fourthByte << 24) + (thirdByte << 16) + (secondByte << 8) + firstByte;
        
        return result;
    }    
}
