package com.canManager.utils;

import com.canManager.MainApp;
import javafx.stage.Stage;

public class Tools {
    private static MainApp main;
    
    public static void setTitlePrimaryStage(String str){
        main.setTitlePrimaryStage(str);
    }

    public static void setMain(MainApp main) {
        Tools.main = main;
        Log.msg(0, "setMain");
    }
    
    public static Stage getPrimaryStage(){
        return main.getPrimaryStage();
    }
    
    public static int stringToInteger(String str){
        if(str!="")
            return Integer.parseInt(str);
        else
            return 0;
    }
}
