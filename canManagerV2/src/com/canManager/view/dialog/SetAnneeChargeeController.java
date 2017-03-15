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
    private int numCatalog, annee;
    private CatalogFile catalogFile = null;
    
    @FXML
    private Label catalogNecLabel;
    
    @FXML
    private ListView listView = new ListView<String>();
    private int anneeAffiche = 0;
    
    public void ini(Stage anneeChargeeStage, int numCatalog, int annee) {
        this.stage = anneeChargeeStage;
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
        stage.close();
    }
    
    @FXML
    private void handleChoice(){
        if(listView.getSelectionModel().getSelectedItem()!=null)
            catalogFile = (CatalogFile) listView.getSelectionModel().getSelectedItem();

        stage.close();
    }
    
    public CatalogFile getCatalogFile(){
        return catalogFile;
    }
}
