package com.canManager.view;

import com.canManager.MainApp;
import com.canManager.utils.Log;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

public class RootLayoutController {
    private AnchorPane catalogLayout; 
    private BorderPane rootLayout;
    
    public RootLayoutController(){      
    }      

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
    }   
    
    @FXML
    private void handleExit(){
        System.exit(0);
    }

    @FXML
    private void handleCloseCatalog(){
        rootLayout.getChildren().remove(catalogLayout);
    }
    
    public void showCatalog(String str){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/canManager/view/Catalog.fxml"));
            catalogLayout = (AnchorPane) loader.load();
            
            CatalogController controller = loader.getController();
            controller.iniCatalog(str);
            
            rootLayout.setCenter(catalogLayout);

        } catch (Exception e) {
            Log.msg(1, e.getMessage());
        }
    }
}
