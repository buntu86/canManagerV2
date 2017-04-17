package com.canManager.view.dialog;

import com.canManager.model.CatalogFile;
import com.canManager.utils.Config;
import com.canManager.utils.Log;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

public class SetAnneeChargeeController {

    private Stage stage;
    private int numCatalog, annee, anneeChargee;
    private Path path = null;
    private CatalogFile catalogFile;

    @FXML
    private Label catalogNecLabel;
    
    @FXML
    private ListView listView = new ListView<String>();
    private int charge = 0;
    
    public void ini(Stage stage, int numCatalog, int annee) {

        this.stage = stage;
        this.numCatalog = numCatalog;
        this.annee = annee;
        this.catalogNecLabel.setText("F" + numCatalog + "/" + annee);
        
        ObservableList<CatalogFile> items = FXCollections.observableArrayList();

        try(Stream<Path> paths = Files.walk(Config.getCatalogDirectory())) {
            
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    if(filePath.toString().indexOf("F" + numCatalog)>0 && filePath.toString().toLowerCase().endsWith(".dbf"))
                    {
                        CatalogFile catalogFile = new CatalogFile(filePath.getParent().toString() + System.getProperty("file.separator") + filePath.getFileName().toString());
                        items.add(catalogFile);
                    }
                }
            });           
            
        }catch(Exception e){
            Log.msg(1, "Erreur lors du scan du dossier " + Config.getCatalogDirectory().toString());
        }    

        listView.setItems(items);        
    }
    
    @FXML
    private void handleCancel(){
        this.stage.close();
        
    }
    
    @FXML
    private void handleChoice(){
        if(listView.getSelectionModel().getSelectedItem()!=null)
        {
            this.catalogFile = (CatalogFile) listView.getSelectionModel().getSelectedItem();
            this.charge = 1;
        }

        this.stage.close();
    }

    public CatalogFile getCatalogFile() {
        return this.catalogFile;
    }

    public int getCharge() {
        return this.charge;
    }
}
