package com.canManager;

import com.canManager.data.ReadDbf;
import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        ReadDbf.setDbfFile("src/com/canManager/ressources/F24116.dbf");
        System.out.println("size " + ReadDbf.getListArticles().size());
        System.out.println("size " + ReadDbf.getListArticles().size());
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
