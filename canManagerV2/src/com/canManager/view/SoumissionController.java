package com.canManager.view;

import com.canManager.data.soumission.CatalogSoum;
import com.canManager.data.soumission.PosSoum;
import com.canManager.data.soumission.Soumission;
import com.canManager.utils.Tools;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

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
        }  
        
        updateViewer();
        
        //Listener change tab
        rootTabPane.getSelectionModel().selectedItemProperty().addListener(
            new ChangeListener<Tab>() {
                @Override
                public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
                    updateViewer();
                }
            }
        ); 
    }
    
    private void updateViewer(){
        int idTab=rootTabPane.getSelectionModel().getSelectedIndex();
        CatalogSoum catalog = Soumission.getCatalogSoumWithIdTab(idTab).get();
        
        System.out.println(catalog.getNumTitre());
    }
}