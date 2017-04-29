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
import java.util.List;
import java.util.stream.Collectors;
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
    private int num, annee, idTab, charge;
    private String titre;
    private ObservableList<PosSoum> listPosSoum = FXCollections.observableArrayList();
    private CatalogFile catalogFile = new CatalogFile();
    
    public CatalogSoum(String str){
        if(str.length()>=93){
            num = Integer.parseInt(str.substring(1, 4));
            annee = Integer.parseInt(str.substring(5, 7));
            titre = str.substring(92).trim().replaceAll("  +", "");

            Log.msg(0, num + " " + annee + " " + titre);

            setAnneeChargeeCatalog();
            setListPositionsSoum();
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
    
    public void setIdTab(int i) {
        this.idTab = i;
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
        return catalogFile.getAnnee();
    }

    public Path getPathDbf(){
        return catalogFile.getPath();
    }
    
    private void setAnneeChargeeCatalog() {
        String str = new String(Config.getCatalogDirectory().toString() + System.getProperty("file.separator") + "F" + this.num + this.annee + ".dbf");
        if(Files.exists(Paths.get(str)))
        {
            Log.msg(0, str + " existe");
            catalogFile.setPath(str);
            charge = 1;
        }

        else
        {
            Log.msg(0, str + " n'existe pas.");
            showSetAnneeChargeeDialog();
        }
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
            charge = controller.getCharge();
            
        } catch (IOException e) {
        e.printStackTrace();
        }        
    }    

    public int getCharge() {
        return charge;
    }
    
    public ObservableList<PosSoum> getListPositionSoum(){
        return listPosSoum;
    }

    private void setListPositionsSoum() {
        if(catalogFile!=null){
            Catalog.setCatalog(catalogFile.getPath().toString());
            
            Log.msg(0, "setListPositionsSoum | catalog:" + catalogFile.getNum());
            
            List<String> list = ReadSoum.getRawData().stream()
                    .filter(line -> line.startsWith("G"+catalogFile.getNum()) && line.substring(41,42).equals("2"))
                    .collect(Collectors.toList());

            for(String element : list)
            {
                listPosSoum.add(new PosSoum(element, catalogFile));
            }
        }        
    }
}
