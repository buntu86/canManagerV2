package com.canManager.data.soumission;

import com.canManager.utils.Log;
import java.util.ArrayList;
import javafx.scene.control.Alert;

public class CatalogSoum {
    private int num, annee, anneeChargee, idTab;
    private String titre;
    private ArrayList<PosSoum> listPosSoum;
    
    CatalogSoum(String str){
        if(str.length()>=8){
            num = Integer.parseInt(str.substring(1, 4));
            annee = Integer.parseInt(str.substring(5, 7));
            titre = str.substring(92).trim().replaceAll("  +", "");
            
            listPosSoum = ReadSoum.getPosSoumCatalog(num);
            
            //System.out.println("num:" + num + " annee:" + annee + " titre:" + titre);
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
    
    public String getNumTitre(){
        return num + " - " + titre;
    }
    
    public String getNum(){
        return Integer.toString(num);
    }
    
    public String getTitre(){
        return titre;
    }
    
    public int getIdTab(){
        return idTab;
    }
}
