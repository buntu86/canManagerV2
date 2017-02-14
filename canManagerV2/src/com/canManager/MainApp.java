package com.canManager;

import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import com.canManager.view.RootLayoutController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;    
    
    @Override
    public void start(Stage primaryStage) { 
        Tools.setMain(this);
        this.primaryStage = primaryStage;
        setTitlePrimaryStage("");
        showRootLayout(); 
        
    }    
    
    public void showRootLayout() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("/com/canManager/view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();
            
            RootLayoutController controller = loader.getController();
            controller.setRootLayout(rootLayout);
            controller.showCatalog("F24117");
            
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            Log.msg(1, e.getMessage());
        }
    }           
    
    public Stage getPrimaryStage() {
        return this.primaryStage;
    }
    
    public BorderPane getRootLayout(){
        return rootLayout;
    }    
    
    public void setTitlePrimaryStage(String str)
    {
        if(!str.isEmpty())
            str = " - " + str;
        this.primaryStage.setTitle("canManager" + str);
    }        

    public static void main(String[] args) {
        launch(args);
    }
}
