package com.canManager.view;

import com.canManager.MainApp;
import com.canManager.model.Soumission;
import com.canManager.utils.Config;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import java.io.File;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

public class RootLayoutController {
    private AnchorPane catalogLayout, soumissionLayout; 
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

    //// CATALOGUE ////
    @FXML
    private void handleCloseCatalog(){
        rootLayout.getChildren().remove(catalogLayout);
        Tools.setTitlePrimaryStage("");
        Log.msg(0, "fermeture du catalogue");
    }
    
    @FXML
    private void handleOpenCatalog(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("DBF file", "*.dbf")
        );       
        fileChooser.setInitialDirectory(Config.getCatalogDirectory().toFile());
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {   
            rootLayout.getChildren().remove(catalogLayout);            
            rootLayout.getChildren().remove(soumissionLayout);            
            showCatalog(selectedFile.toString());
        }   
        
        else
            System.out.println("Selection du fichier annulé");  
        
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
    
    //// SOUMISSION ////
    @FXML
    private void handleCloseSoumission(){
        rootLayout.getChildren().remove(soumissionLayout);
        Tools.setTitlePrimaryStage("");        
        Log.msg(0, "fermeture de la soumission");
    }
    
    @FXML
    private void handleOpenSoumission(){
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("SIA451 file", "*.01s")
        );       
        fileChooser.setInitialDirectory(Config.getCatalogDirectory().toFile());
        File selectedFile = fileChooser.showOpenDialog(null);
        if(selectedFile != null)
        {   
            rootLayout.getChildren().remove(catalogLayout);            
            rootLayout.getChildren().remove(soumissionLayout); 

            showSoumission(selectedFile.toString());
        }   
        
        else
            System.out.println("Selection du fichier annulé");  
    }
    
    public void showSoumission(String str){        
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/canManager/view/Soumission.fxml"));
            soumissionLayout = (AnchorPane) loader.load();
            
            SoumissionController controller = loader.getController();
            controller.iniSoumission(str);
            
            rootLayout.setCenter(soumissionLayout);

        } catch (Exception e) {
            Log.msg(1, e.getMessage());
        }
    }    

}
