package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.util.ArrayList;
import java.util.Optional;

public class Soumission {

    private static String nomMandat="";
    private static int numMandat=0;
    
    public static void setSoumission(String pathSoum){
        ReadSoum.setFile(pathSoum);
        ListCatalogSoum listCatalogSoum = new ListCatalogSoum();        
        numMandat = getNumMandat();
        nomMandat = getNomMandat();
        Log.msg(0, "iniCatalogSoumission");
    }

    public static String getTitle() {
        return numMandat + " " + nomMandat;
    }
    
    public static int getNumMandat(){
        String numMandatFromFile = new String();
        int numMandat=0;

        numMandatFromFile = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        numMandatFromFile=numMandatFromFile.substring(75, 84).replaceAll(" ", "");
        if(!numMandatFromFile.isEmpty())
            numMandat=Integer.parseInt(numMandatFromFile);

        return numMandat;        
    }

    public static String getNomMandat(){
        String nomMandatFromFile = new String(), nomMandat = new String();
        
        nomMandatFromFile = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        nomMandat=nomMandatFromFile.substring(92, 122).trim();

        return nomMandat;        
    }  
    
    /*public static ArrayList<CatalogSoum> getAllCatalogSoum(){
        return listCatalogSoum;
    }

    public static Optional<CatalogSoum> getCatalogSoumWithIdTab(int idTab) {
         return listCatalogSoum
                .stream()
                .filter(catalog -> catalog.getIdTab()==idTab)
                .findFirst();
    }

    public static Optional<CatalogSoum> getCatalogSoumWithNumCat(int numCatalog) {
         return listCatalogSoum
                .stream()
                .filter(catalog -> catalog.getNum()==numCatalog)
                .findFirst();
    }    */
}