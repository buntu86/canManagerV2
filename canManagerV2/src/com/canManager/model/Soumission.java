package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.util.ArrayList;
import java.util.Optional;


public class Soumission {

    private static ArrayList<CatalogSoum> listCatalogSoum = new ArrayList<>();
    private static String nomMandat="";
    private static int numMandat=0;
    
    public static void setSoumission(String pathSoum){
        ReadSoum.setFile(pathSoum);
        listCatalogSoum = ReadSoum.getCatalogSoum();        
        numMandat = ReadSoum.getNumMandat();
        nomMandat = ReadSoum.getNomMandat();
        Log.msg(0, "iniCatalogSoumission");
    }

    public static String getTitle() {
        return numMandat + " " + nomMandat;
    }
    
    public static ArrayList<CatalogSoum> getAllCatalogSoum(){
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
    }    
}