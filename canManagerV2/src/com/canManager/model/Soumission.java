package com.canManager.model;

import com.canManager.MainApp;
import com.canManager.data.ReadSoum;
import com.canManager.utils.Config;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import com.canManager.view.dialog.SetAnneeChargeeController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Optional;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Soumission {

    private static ArrayList<CatalogSoum> listCatalogSoum = new ArrayList<>();
    private static String nomMandat="";
    private static int numMandat=0;
    
    public static void setSoumission(String pathSoum){
        ReadSoum.setPathSoumission(pathSoum);
        listCatalogSoum = ReadSoum.getCatalogSoum();
        numMandat = ReadSoum.getNumMandat();
        nomMandat = ReadSoum.getNomMandat();
        setAnneeChargeeCatalog();
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
    
    
    private static void setAnneeChargeeCatalog() {
        for(CatalogSoum catalog : listCatalogSoum){
            String str = new String(Config.getCatalogDirectory().toString() + System.getProperty("file.separator") + "F" + catalog.getNum() + catalog.getAnnee() + ".dbf");
            Log.msg(0, "setAnneeChargeeCatalog | " + str);
            if(Files.exists(Paths.get(str)))
            {
                catalog.setAnneeAffiche(catalog.getAnnee());
                catalog.setPathDbf(str);
            }

            else
            {
                Log.msg(1, "setAnneeChargee " + catalog.getNum() + catalog.getAnnee());
                showSetAnneeChargeeDialog(catalog.getNum(), catalog.getAnnee());
            }
        }
    }

    private static void showSetAnneeChargeeDialog(int numCatalog, int annee) {
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/canManager/view/dialog/SetAnneeChargee.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage anneeChargeeStage = new Stage();
            anneeChargeeStage.setTitle("Année chargée");
            anneeChargeeStage.initModality(Modality.WINDOW_MODAL);
            anneeChargeeStage.initOwner(Tools.getPrimaryStage());
            Scene scene = new Scene(page);
            anneeChargeeStage.setScene(scene);
            
            SetAnneeChargeeController controller = loader.getController();
            controller.ini(anneeChargeeStage, numCatalog, annee);
            
            anneeChargeeStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
                if(t.getCode()==KeyCode.ESCAPE)
                    anneeChargeeStage.close();
            });            
            
            anneeChargeeStage.showAndWait();
            
        } catch (IOException e) {
        e.printStackTrace();
        }        
    }
}