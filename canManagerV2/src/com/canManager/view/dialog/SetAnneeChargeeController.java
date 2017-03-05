package com.canManager.view.dialog;

import com.canManager.model.Soumission;
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
    
    @FXML
    private Label catalogNecLabel;
    
    @FXML
    private ListView listView = new ListView<String>();
    
    public void ini(Stage anneeChargeeStage, int numCatalog, int annee) {
        this.stage = anneeChargeeStage;
        this.numCatalog = numCatalog;
        this.annee = annee;
        this.catalogNecLabel.setText("F" + numCatalog + "/" + annee);

        ObservableList<String> items = FXCollections.observableArrayList();
        try(Stream<Path> paths = Files.walk(Config.getCatalogDirectory())) {
            paths.forEach(filePath -> {
                if (Files.isRegularFile(filePath)) {
                    if(filePath.toString().indexOf("F" + numCatalog)>0 && filePath.toString().toLowerCase().endsWith(".dbf"))
                        items.add(filePath.getFileName().toString());
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
        {
            int select = Integer.parseInt(listView.getSelectionModel().getSelectedItem().toString().substring(4, 6));
            Soumission.getCatalogSoumWithNumCat(numCatalog).get().setAnneeAffiche(select);
            Soumission.getCatalogSoumWithNumCat(numCatalog).get().setPathDbf(Config.getCatalogDirectory() + System.getProperty("file.separator") + listView.getSelectionModel().getSelectedItem().toString());
        }

        stage.close();
    }
}
