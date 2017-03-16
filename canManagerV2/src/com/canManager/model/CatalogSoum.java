package com.canManager.model;

import com.canManager.MainApp;
import com.canManager.data.ReadSoum;
import com.canManager.utils.Config;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import com.canManager.view.dialog.SetAnneeChargeeController;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CatalogSoum {
    private int num, annee, anneeChargee, idTab;
    private String titre;
    private ObservableList<PosSoum> listPosSoum = FXCollections.observableArrayList();
    private Path pathDbf=null;
    private CatalogFile catalogFile = null;
    
    public CatalogSoum(String str){

        if(str.length()>=93){
            num = Integer.parseInt(str.substring(1, 4));
            annee = Integer.parseInt(str.substring(5, 7));
            setAnneeChargeeCatalog();
            titre = str.substring(92).trim().replaceAll("  +", "");
            //listPosSoum = ReadSoum.getPosSoumCatalog(num);
        }
        else
        {
            Log.msg(1, "Taille catalogSoum de *.01s");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Un problème est survenu lors de l'import du fichier *.01s\nErreur:catalogSoum");
            alert.showAndWait();
        }
    }
    
    public void setAnneeAffiche(int annee){
        this.anneeChargee = annee;
    }
    public void setIdTab(int i) {
        this.idTab = i;
    }    
    public void setPathDbf(String str){
        this.pathDbf = Paths.get(str);
    }
    
    public String getNumTitre(){
        return num + " - " + titre;
    }
    
    public int getNum(){
        return num;
    }
    
    public String getTitre(){
        return titre;
    }

    public int getAnnee(){
        return annee;
    }
    
    public int getIdTab(){
        return idTab;
    }

    public int getAnneeAffiche() {
        return anneeChargee;
    }

    public Path getPathDbf(){
        if(catalogFile!=null)
            return catalogFile.getPath();
        else
            return null;
    }
    
    
    public ObservableList<PosSoum> getListPos() {
        return listPosSoum;
    }
    
    private void setAnneeChargeeCatalog() {
        String str = new String(Config.getCatalogDirectory().toString() + System.getProperty("file.separator") + "F" + this.num + this.annee + ".dbf");
        Log.msg(0, "setAnneeChargeeCatalog | " + str);
        if(Files.exists(Paths.get(str)))
        {
            catalogFile.setPath(str);
            this.anneeChargee=this.annee;
        }

        else
            showSetAnneeChargeeDialog();
    }

    private void showSetAnneeChargeeDialog() {
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
            controller.ini(anneeChargeeStage, num, annee);
            
            anneeChargeeStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
                if(t.getCode()==KeyCode.ESCAPE)
                    anneeChargeeStage.close();
            });            
            
            anneeChargeeStage.showAndWait();
            
            catalogFile = controller.getCatalogFile();
            
        } catch (IOException e) {
        e.printStackTrace();
        }        
    }    
}
