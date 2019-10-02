package com.canManager.view;

import com.canManager.MainApp;
import com.canManager.data.ExportToCsv;
import com.canManager.utils.Config;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import com.canManager.view.dialog.ConfigController;
import java.io.File;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RootLayoutController {
    private AnchorPane catalogLayout, soumissionLayout; 
    private BorderPane rootLayout;
    private String strSelectedFile = "";
    @FXML private MenuItem exportCatalog;
    
    public RootLayoutController(){ 

    }      

    public void setRootLayout(BorderPane rootLayout) {
        this.rootLayout = rootLayout;
        exportCatalog.setDisable(true);
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
        exportCatalog.setDisable(true);
        Log.msg(0, "fermeture du catalogue");
    }

    @FXML
    private void exportCatalog(){
        ExportToCsv export = new ExportToCsv(strSelectedFile);
        Log.msg(0, "export du catalogue");
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
            strSelectedFile = selectedFile.toString();
            showCatalog(strSelectedFile);
            exportCatalog.setDisable(false);
        }   
        
        else
        {
            strSelectedFile = "";
            System.out.println("Selection du fichier annulé");
        }  
        
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
            Log.msg(1, "showCatalog | " + e.getMessage());
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
            Log.msg(1, "showSoumission | " + e.getMessage());
        }
    }   
    
    //// EDITION ////
    @FXML
    public void showConfigDialog(){        
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/canManager/view/dialog/Config.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            Stage configStage = new Stage();
            configStage.setTitle("Configuration");
            configStage.initModality(Modality.WINDOW_MODAL);
            configStage.initOwner(Tools.getPrimaryStage());
            Scene scene = new Scene(page);
            configStage.setScene(scene);
            
            ConfigController controller = loader.getController();
            controller.ini(configStage);
            
            configStage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent t) -> {
                if(t.getCode()==KeyCode.ESCAPE)
                {
                    configStage.close();
                    Log.msg(0, "Dialog config : esc");
                }
            });            
            
            configStage.showAndWait();            
        } catch (IOException e) {
        e.printStackTrace();
        }     
    }   
}
