package com.canManager.view;

import com.canManager.model.Articles;
import com.canManager.model.Catalog;
import com.canManager.utils.Tools;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

public class CatalogController implements Initializable {

    @FXML
    private TreeView<String> rootTree;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    void iniCatalog(String str) {
        Catalog.setCatalog(str);
        Tools.setTitlePrimaryStage(Catalog.getTitle());
        TreeItem<String> tree = new TreeItem<> ("CAN");
        
        for(Articles artLev1 : Catalog.getLevel1()){
            TreeItem<String> level1 = new TreeItem<> (artLev1.getPos() + " - " + artLev1.getTextTitle());
            
            for(Articles artLev2 : Catalog.getLevel2(artLev1.getPosInt())){
                TreeItem<String> level2 = new TreeItem<> (artLev2.getPos() + " - " + artLev2.getTextTitle());
            
                for(Articles artLev3 : Catalog.getLevel3(artLev2.getPosInt())){
                    TreeItem<String> level3 = new TreeItem<> (artLev3.getPos() + " - " + artLev3.getTextTitle());
                    level2.getChildren().add(level3);
                }
                level1.getChildren().add(level2);
            }
            tree.getChildren().add(level1);
        }
        
        rootTree.setRoot(tree);
        rootTree.setShowRoot(false);
    }
}
