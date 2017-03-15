package com.canManager.data;

import com.canManager.model.Catalog;
import com.canManager.model.PosSoum;
import com.canManager.model.CatalogSoum;
import com.canManager.utils.Log;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class ReadSoum {
    private static Path pathSoum, pathCatalog;
    private static ArrayList<PosSoum> listPosSoum = new ArrayList<>();
    private static List<String> file = null;
    
    public static void setPathSoumission(String pathSoum) {
        ReadSoum.pathSoum = Paths.get(pathSoum);
        ReadSoum.file = null;
        Log.msg(0, "setPathSoumission - " + pathSoum);
    }
    
    /*public static void setPathCatalog(String pathCatalog){
        Catalog.setCatalog(pathCatalog);
    }*/

    public static ArrayList<CatalogSoum> getCatalogSoum() {
        ArrayList<CatalogSoum> catalogSoum = new ArrayList<>();

        List<String> list = getFile().stream()
                .filter(line -> line.startsWith("G") && line.substring(41,42).equals("1"))
                .collect(Collectors.toList());
        
        for(String element : list)
        {
            catalogSoum.add(new CatalogSoum(element));
        }

        Log.msg(0, "getCatalogSoum");
        
        return catalogSoum;        
    }

    public static ObservableList<PosSoum> getPosSoumCatalog(int num){
        ObservableList<PosSoum> listPosSoum = FXCollections.observableArrayList(); 
        
        List<String> listPosFromFile = getFile().stream()
                .filter(line -> line.startsWith("G"+num) && line.substring(41,42).equals("2"))
                .collect(Collectors.toList());

        for(String element : listPosFromFile)
        {
            String pos = element.substring(7, 10);
            String upos = element.substring(10, 13);
            
            String um = getUm(num, pos, upos);
            double quantite = getQuantite(num, pos, upos);
            

            String desc = new String();
            List<String> listPos = getFile().stream()
                    .filter(line -> line.startsWith("G"+num+"   "+pos+upos) && (line.substring(41,42).equals("2") || line.substring(41,42).equals("3")))
                    .collect(Collectors.toList());
            
            for(String element1 : listPos){
                // ################################
                // ################################
                //desc = Catalog.getArticle(pos, upos, "00");
                // ################################
                // ################################
            
                if(element1.length()>92 && !element1.substring(41,42).equals("2"))
                    desc = desc + element1.substring(92, element1.length()) + "\n";
            }
            
            listPosSoum.add(new PosSoum(pos, upos, desc, um, quantite));
        }
   
        return listPosSoum;
    }    
    
    public static int getNumMandat(){
        String numMandatFromFile = new String();
        int numMandat=0;

        numMandatFromFile = getFile().stream()
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
        
        nomMandatFromFile = getFile().stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        nomMandat=nomMandatFromFile.substring(92, 122).trim();

        return nomMandat;        
    }

    private static String getUm(int num, String pos, String upos) {
        String um = "";
        
        List<String> listFromFile = getFile().stream()
                .filter(line -> line.startsWith("G" + num + "   " + pos + upos) && line.substring(41,42).equals("5"))
                .collect(Collectors.toList());

        for(String element : listFromFile)
            um = element.substring(58, element.length());

        return um;
    }    

    private static double getQuantite(int num, String pos, String upos) {
        double quantite = 0;
        List<String> listFromFile = getFile().stream()
                .filter(line -> line.startsWith("G" + num + "   " + pos + upos) && line.substring(41,42).equals("6"))
                .collect(Collectors.toList());

        for(String element : listFromFile)
        {
            if(element.length()>=58)
                quantite = Double.parseDouble(element.substring(45, 58))/1000;
        }
            
        return quantite;
    }    
    
    private static List<String> getFile() {        
        if(ReadSoum.file==null)
            setFile();
        
        return ReadSoum.file;            
    }
    
    public static void setFile(){
        try{
            if(ReadSoum.file==null)
                ReadSoum.file = Files.readAllLines(pathSoum, Charset.forName("IBM437"));
        }catch(Exception e){
            Log.msg(1, "Le fichier .01s ne peut-être lu.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + pathSoum.toString() + "\" ne peut-être lu.\n" + e.getMessage());
            alert.showAndWait();  
        }
        Log.msg(0, "setFile() - " + ReadSoum.file.size() + " lines");
    }
}
