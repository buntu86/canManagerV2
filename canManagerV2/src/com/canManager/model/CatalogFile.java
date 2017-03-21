package com.canManager.model;

import com.canManager.utils.Tools;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CatalogFile {
    
    private String nomCatalogFile = "";
    private Path pathCatalogFile = Paths.get("");
    private int numCatalogFile=0, anneeCatalogFile=0;
    
    public CatalogFile(){
    }
    
    public CatalogFile(String str) {
        setPath(str);
    }
    
    public void setPath(String str){
        pathCatalogFile = Paths.get(str);
        nomCatalogFile = pathCatalogFile.getFileName().toString();        
        if(nomCatalogFile.length()>=7)
        {
            numCatalogFile = Tools.stringToInteger(nomCatalogFile.substring(1, 4));
            anneeCatalogFile = Tools.stringToInteger(nomCatalogFile.substring(4, 6));
        }
    }
    
    public int getNum(){
        return numCatalogFile;
    }
    
    public int getAnnee(){
        return anneeCatalogFile;
    }
    
    public Path getPath(){
        return pathCatalogFile;
    }
    
    @Override
    public String toString(){
        return this.nomCatalogFile;
    }
}
