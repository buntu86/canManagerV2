package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.nio.file.Path;
import java.nio.file.Paths;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class CatalogSoum {
    private int num, annee, anneeChargee, idTab;
    private String titre;
    private ObservableList<PosSoum> listPosSoum = FXCollections.observableArrayList();
    private Path pathDbf=null;
    
    public CatalogSoum(String str){
        if(str.length()>=93){
            num = Integer.parseInt(str.substring(1, 4));
            annee = Integer.parseInt(str.substring(5, 7));
            titre = str.substring(92).trim().replaceAll("  +", "");
            listPosSoum = ReadSoum.getPosSoumCatalog(num, pathDbf);
        }
        else
        {
            Log.msg(1, "Taille catalogSoum de *.01s");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Soumission");
            alert.setHeaderText(null);
            alert.setContentText("Un problème est survenu lors de l'import du fichier *.01s\nErreur:catalogSoum");
            alert.showAndWait();
        }
    }
    
    public void setAnneeAffiche(int annee){
        this.anneeChargee = annee;
    }
    public void setIdTab(int i) {
        this.idTab = i;
    }    
    public void setPathDbf(String str){
        this.pathDbf = Paths.get(str);
    }
    
    public String getNumTitre(){
        return num + " - " + titre;
    }
    
    public int getNum(){
        return num;
    }
    
    public String getTitre(){
        return titre;
    }

    public int getAnnee(){
        return annee;
    }
    
    public int getIdTab(){
        return idTab;
    }

    public int getAnneeAffiche() {
        return anneeChargee;
    }

    public Path getPathDbf(){
        return pathDbf;
    }
    
    public ObservableList<PosSoum> getListPos() {
        return listPosSoum;
    }
}
