package com.canManager.data.soumission;

import com.canManager.utils.Log;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import javafx.scene.control.Alert;

public class Soumission {

    private static ArrayList<CatalogSoum> listCatalogSoum = new ArrayList<>();
    private static String titreMandat;
    private static int numMandat;
    
    public static boolean setSoumission(String pathSoum){
        if(Files.exists(Paths.get(pathSoum)))
        {
            ReadSoum.setPathSoumission(pathSoum);
            listCatalogSoum = ReadSoum.getCatalogSoum();
            Log.msg(0, "iniCatalogSoumission");
            return true;
        }
        else
        {
            Log.msg(1, "Le fichier .01s n'existe pas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + pathSoum.toString() + "\" n'existe pas.");
            alert.showAndWait();
            return false;        
        }
    }

    public static String getTitle() {
        return "soumissionTitle";
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
}