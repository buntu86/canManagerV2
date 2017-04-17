package com.canManager.view.dialog;

import com.canManager.utils.Log;
import com.canManager.utils.Config;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class ConfigController {
    private Stage stage;
    
    @FXML
    private TextField label_pathCatalogDirectory;    

    public void ini(Stage stage)
    {
        this.stage = stage;
        label_pathCatalogDirectory.setText(Config.getCatalogDirectory().toString());
        Log.msg(0, "Dialog config : open");
    }

    @FXML
    private void handleCancel(){
        stage.close();
        Log.msg(0, "Dialog config : cancel");
    }
    
    @FXML
    private void handleSave(){
        Config.setCatalogDirectory(label_pathCatalogDirectory.getText());
        stage.close();
        Log.msg(0, "Dialog config : save");
    }
}
