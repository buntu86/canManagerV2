package com.canManager.view;

import com.canManager.MainApp;
import com.canManager.model.CatalogSoum;
import com.canManager.model.Soumission;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import java.io.IOException;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

public class SoumissionController {

    @FXML
    private TabPane rootTabPane = new TabPane();
    
    public void iniSoumission(String str) {
        Soumission.setSoumission(str);
        Tools.setTitlePrimaryStage(Soumission.getTitle());
        int i=0;
        
        for(CatalogSoum catalog:Soumission.getAllCatalogSoum())
        {
            String strCatalog = new String(catalog.getNumTitre());
            if(strCatalog.length()>15)
                strCatalog = strCatalog.substring(0, 15)+"..";
            
            catalog.setIdTab(i);
            i++;
            
            rootTabPane.getTabs().add(new Tab(strCatalog));  
            
            if(catalog.getAnneeAffiche()==0)
                rootTabPane.getTabs().get(catalog.getIdTab()).setDisable(true);
        }  
        updateViewer();
        
        //Listener change tab
        rootTabPane.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends Tab> ov, Tab t, Tab t1) -> {
            updateViewer();
        });
    }
    
    private void updateViewer(){
        int idTab=rootTabPane.getSelectionModel().getSelectedIndex();
        CatalogSoum catalog = Soumission.getCatalogSoumWithIdTab(idTab).get();

        if(!rootTabPane.getSelectionModel().getSelectedItem().isDisable())
        {            
            AnchorPane table = null;
            try {
                int index=rootTabPane.getSelectionModel().getSelectedIndex();
                
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(MainApp.class.getResource("view/SoumissionTable.fxml"));
                table = (AnchorPane) loader.load();
                SoumissionTableController controller = loader.getController();
                controller.ini(index);
                
                rootTabPane.getSelectionModel().getSelectedItem().setContent(table);
            }
            catch(IOException e){
                Log.msg(1, "Erreur file SoumissionTable.fxml" + e.getMessage());
            }
        }        
    }
}

