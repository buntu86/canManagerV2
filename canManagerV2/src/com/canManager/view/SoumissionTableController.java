package com.canManager.view;

import com.canManager.model.CatalogSoum;
import com.canManager.model.PosSoum;
import com.canManager.model.Soumission;
import com.canManager.utils.Log;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class SoumissionTableController implements Initializable{
    
    @FXML
    private TableView<PosSoum> table;
    
    @FXML
    private TableColumn<PosSoum, String> articleColumn;

    @FXML
    private TableColumn<PosSoum, String> descColumn;
    
    @FXML
    private TableColumn<PosSoum, String> quantiteColumn;
    
    @FXML
    private TableColumn<PosSoum, String> umColumn;
    
    int indexTab=0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        articleColumn.setCellValueFactory(cellData -> cellData.getValue().articleProperty());
        /*descColumn.setCellValueFactory(cellData -> cellData.getValue().descProperty());
        quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
        umColumn.setCellValueFactory(cellData -> cellData.getValue().umProperty());*/
    }    
    
    public void ini(int index) {
        this.indexTab = index;
        
        CatalogSoum catSoum = Soumission.getCatalogSoumWithIdTab(indexTab).get();
        
        Log.msg(0, "ini : catalog " + catSoum.getNumTitre() + " | " + Soumission.getCatalogSoumWithIdTab(indexTab).get().getPathDbf().toString());
        
        for(PosSoum pos : catSoum.getListPositionSoum()){
            Log.msg(0, "ini : posSoum " + pos.getArticle());
        }
        
        //table.setItems(catSoum.getListPositionSoum());
        
        /*
        for(PosSoum pos : Soumission.getCatalogSoumWithIdTab(indexTab).get().getListPos()){
            //System.out.println(pos.getPos() + "." + pos.getUpos() + "|desc " + pos.getDesc() + "|um " + pos.getUm() + "|Q " + pos.getQuantite());
        }
        
        table.setItems(Soumission.getCatalogSoumWithIdTab(indexTab).get().getListPos());*/
    }
}

