package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import java.util.List;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PosSoum {
    private double quantite=0, prix=0, montant=0;
    private int pos=0, upos=0, var=0;
    private String um = "", desc = "";
    private CatalogFile catFile = new CatalogFile();
    private final SimpleStringProperty article;
    
    public PosSoum(String str, CatalogFile catFile){
        this.catFile = catFile;
        
        if(str.length()>=42){
            this.pos = Tools.stringToInteger(str.substring(7, 10));
            this.upos = Tools.stringToInteger(str.substring(10, 13));
            setUm(); 
            setQuantite();    
            setDesc();
        }

        this.article = new SimpleStringProperty(String.format("%03d", pos) + "." + String.format("%03d", upos));

        
        Log.msg(0, catFile.getNum() + " " + catFile.getAnnee() + " | " + String.format("%03d", pos) + "." + String.format("%03d", upos) + " " + String.format("%02d", var) + " | um " + um + " | Q " + quantite + " | desc " + desc);
    }
    
    //SET
    ///// try findFirst!!!!!!
    private void setUm() {
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + this.pos + this.upos) && line.substring(41,42).equals("5"))
                .collect(Collectors.toList());

        for(String element : list)
        {
            if(element.length()>=58)
                um = element.substring(58, element.length());
        }
    }   
    

    ///// try findFirst!!!!!!
    private void setQuantite() {
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + pos + upos) && line.substring(41,42).equals("6"))
                .collect(Collectors.toList());

        for(String element : list)
        {
            if(element.length()>=58)
                quantite = Double.parseDouble(element.substring(45, 58))/1000;
        }
    }    

    private void setDesc(){
        int lastVar=0;
        
        List<String> listPos = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + pos + upos) && (line.substring(41,42).equals("2") || line.substring(41,42).equals("3")))
                .collect(Collectors.toList());        
        
        if(listPos.size()>1){
            //Line 0 == 2
            listPos.remove(0);
            String tmp = "";
            int firstLoop=1;
            
            for(String element : listPos){
                int varTmp = 0;

                if(element.length()>=15);
                    varTmp = Tools.stringToInteger(element.substring(13,15));

                if(lastVar!=varTmp)
                {
                    lastVar=varTmp;                   
                    if(firstLoop!=1)
                    {
                        firstLoop=0;
                        
                        if(element.length()>=57)
                            tmp += element.substring(57).trim();

                        tmp += Catalog.getArticle(pos, upos, lastVar);
                        
                        tmp += "\n";
                    }
                    else
                    {
                        tmp += Catalog.getArticle(pos, upos, lastVar);
                        
                        if(element.length()>=57)
                            tmp += element.substring(57).trim();
                        
                        tmp += "\n";
                    }                        
                }
                else
                {
                    firstLoop=1;
                    if(element.length()>=57)
                        tmp += element.substring(57).trim() + "\n";                
                }
            }   
            
            desc = tmp.trim();           
        }

        else            
            desc = Catalog.getArticle(pos, upos, var);
    }     
    
    //PROPERTY
    public StringProperty articleProperty() {
        return article;
    }    
    
    //GET
    public String getArticle(){
        return article.get();
    }
}
