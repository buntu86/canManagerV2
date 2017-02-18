package com.canManager.data.soumission;

import com.canManager.utils.Log;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.Alert;

public class ReadSoum {
    private static Path pathSoum;
    private static ArrayList<PosSoum> listPosSoum = new ArrayList<>();
    //https://www.mkyong.com/java8/java-8-stream-read-a-file-line-by-line/
    
    static void setPathSoumission(String pathSoum) {
       ReadSoum.pathSoum = Paths.get(pathSoum);
    }

    protected static ArrayList<CatalogSoum> getCatalogSoum() {
        ArrayList<CatalogSoum> catalogSoum = new ArrayList<>();
        List<String> list = new ArrayList<>();
        String lastElement=""; 
        
        try(Stream<String> stream = Files.lines(pathSoum, Charset.forName("IBM437")))
        {
            list = stream
                    .filter(line -> line.startsWith("G"))
                    .collect(Collectors.toList());

            for(String element : list){
                if(lastElement.length()<3 || !lastElement.substring(1, 4).equals(element.substring(1, 4)))
                    catalogSoum.add(new CatalogSoum(element));
                
                lastElement=element;
            }
        }catch(Exception e){
            Log.msg(1, "Le fichier .01s ne peut-être lu.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + pathSoum.toString() + "\" ne peut-être lu.\n" + e.getMessage());
            alert.showAndWait();            
        }
        
        return catalogSoum;        
    }

    protected static ArrayList<PosSoum> getPosSoumCatalog(int num){
        List<String> list = new ArrayList<>();
        String lastElement="";
        ArrayList<PosSoum> listPosSoum = new ArrayList<>();
        
        System.out.println("ok ------------------ ");
        
        
        try(Stream<String> stream = Files.lines(pathSoum, Charset.forName("IBM437")))
        {
            list = stream
                    .filter(line -> line.startsWith("G"+num))
                    .collect(Collectors.toList());
            
            for(String element : list)
                System.out.println(element);
            
        }catch(Exception e){
            Log.msg(1, "Le fichier .01s ne peut-être lu.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + pathSoum.toString() + "\" ne peut-être lu.\n" + e.getMessage());
            alert.showAndWait();            
        }
        
        return listPosSoum;
    }    
}
