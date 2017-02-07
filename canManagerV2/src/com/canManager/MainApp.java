package com.canManager;

import com.canManager.data.DbfToCms;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        DbfToCms.convert("src/com/canManager/ressources/F24117.dbf");
        System.exit(0);    
    }

    public static void main(String[] args) {
        launch(args);
    }
}
