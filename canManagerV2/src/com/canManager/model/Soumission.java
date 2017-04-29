package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Soumission {

    private static String nomMandat="";
    private static int numMandat=0;
    private static ArrayList<CatalogSoum> listCatalogSoum = new ArrayList<>();
    
    public static void setSoumission(String pathSoum){
        ReadSoum.setFile(pathSoum);
        setNumMandat();
        setNomMandat();
        setListCatalogSoum();
        Log.msg(0, "iniCatalogSoumission");
    }
    
    //SET
    public static void setNumMandat(){
        String numMandatFromFile = new String();

        numMandatFromFile = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        numMandatFromFile=numMandatFromFile.substring(75, 84).replaceAll(" ", "");
        if(!numMandatFromFile.isEmpty())
            numMandat=Integer.parseInt(numMandatFromFile);

        Log.msg(0, "setNumMandat " + numMandat);

    }

    public static void setNomMandat(){
        String nomMandatFromFile = new String();
        
        nomMandatFromFile = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        nomMandat=nomMandatFromFile.substring(92, 122).trim();

        Log.msg(0, "setNomMandat " + nomMandat);
    }

    private static void setListCatalogSoum() {
        listCatalogSoum = new ArrayList();
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G") && line.substring(41,42).equals("1"))
                .collect(Collectors.toList());
        
        Log.msg(0, "constructor : nbrCatalog " + list.size());
        for(String element : list)
        {
            listCatalogSoum.add(new CatalogSoum(element));
        }
    }
        
    //GET    
    public static ArrayList<CatalogSoum> getListCatalogSoum(){
        Log.msg(0, "getListCatalogSoum : listCatalogSoum.size() = " + listCatalogSoum.size());
        return listCatalogSoum;
    }
    
    public static String getTitle() {
        return numMandat + " " + nomMandat;
    }    
    public static Optional<CatalogSoum> getCatalogSoumWithIdTab(int idTab) {
         return listCatalogSoum
                .stream()
                .filter(catalog -> catalog.getIdTab()==idTab)
                .findFirst();
    }
}        


    /*

    public static Optional<CatalogSoum> getCatalogSoumWithNumCat(int numCatalog) {
         return listCatalogSoum
                .stream()
                .filter(catalog -> catalog.getNum()==numCatalog)
                .findFirst();
    }    */
