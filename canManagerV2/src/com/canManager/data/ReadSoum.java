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
        }
        Log.msg(1, "setFile() fail");
    }

    public static List<String> getRawData(){
        if(ReadSoum.file==null)
        {
            Log.msg(1, "getRawData == null");
            return null;
        }
        else{
            Log.msg(0, "getRawData - " + ReadSoum.file.size() + " lines");
            return ReadSoum.file;
        }
    }
    
    public static int getNumMandat(){
        String numMandatFromFile = new String();
        int numMandat=0;

        numMandatFromFile = ReadSoum.file.stream()
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
        
        nomMandatFromFile = ReadSoum.file.stream()
                .filter(line -> line.startsWith("A"))
                .findFirst()
                .get();

        nomMandat=nomMandatFromFile.substring(92, 122).trim();

        return nomMandat;        
    }    
    
}


/*


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
        
        return ReadSoum.file;            
    }*/