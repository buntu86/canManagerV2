package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import com.canManager.utils.Tools;
import java.util.List;
import java.util.stream.Collectors;

public class PosSoum {
    private double quantite=0, prix=0, montant=0;
    private int pos=0, upos=0;
    private String um = "", desc = "";
    private CatalogFile catFile = new CatalogFile();
    
    public PosSoum(String str, CatalogFile catFile){
        this.catFile = catFile;
        
        if(str.length()>=42){
            this.pos = Tools.stringToInteger(str.substring(7, 10));
            this.upos = Tools.stringToInteger(str.substring(10, 13));
            setUm(); 
            setQuantite();    
            //setDesc();
        }
        
        Log.msg(0, catFile.getNum() + " " + catFile.getAnnee() + " | " + pos + "." + upos + " | um " + um + " | Q " + quantite);
    }
    
    ///// try findFirst!!!!!!
    private String setUm() {
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + this.pos + this.upos) && line.substring(41,42).equals("5"))
                .collect(Collectors.toList());

        for(String element : list)
        {
            if(element.length()>=58)
                um = element.substring(58, element.length());
        }
        
        return this.um;        

        /*String element = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + this.pos + this.upos) && line.substring(41,42).equals("5"))
                .findFirst()
                .get();
        
        Log.msg(0, "element " + element.length());
        
        if(element.length()>=58)
            um = element.substring(58, element.length());

        return this.um;*/
    }   
    

    ///// try findFirst!!!!!!
    private double setQuantite() {
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G" + this.catFile.getNum() + "   " + pos + upos) && line.substring(41,42).equals("6"))
                .collect(Collectors.toList());

        for(String element : list)
        {
            if(element.length()>=58)
                quantite = Double.parseDouble(element.substring(45, 58))/1000;
        }
            
        return quantite;
    }    

    
    /*
        String desc = new String();
        List<String> listPos = getFile().stream()
                .filter(line -> line.startsWith("G"+num+"   "+pos+upos) && (line.substring(41,42).equals("2") || line.substring(41,42).equals("3")))
                .collect(Collectors.toList());

        for(String element1 : listPos){
            // ################################
            // ################################
            //desc = Catalog.getArticle(pos, upos, "00");
            // ################################
            // ################################

            if(element1.length()>92 && !element1.substring(41,42).equals("2"))
                desc = desc + element1.substring(92, element1.length()) + "\n";
        }

*/    
    /*
    public PosSoum(String pos, String upos, String desc, String um, double quantite){
        this.article = new SimpleStringProperty();
        this.pos = new SimpleStringProperty(pos);
        this.upos = new SimpleStringProperty(upos);
        this.desc = new SimpleStringProperty(desc);
        this.um = new SimpleStringProperty(um);
        this.quantite = quantite;
    }
 
    public String getPos(){
        return this.pos.get();
    }
    public StringProperty articleProperty() {     
        return new SimpleStringProperty(this.pos.get() + "." + this.upos.get());
    }        
    
    public String getUpos(){
        return this.upos.get();
    }
    
    public String getDesc(){
        return this.desc.get();
    }
    public StringProperty descProperty() {     
        return this.desc;
    }            
    
    public String getUm(){
        return this.um.get();
    }
    public StringProperty umProperty() {     
        return this.um;
    }    
    
    public double getQuantite(){
        return this.quantite;
    }
    public StringProperty quantiteProperty() {     
        return new SimpleStringProperty(Double.toString(quantite));
    }        
    
    public double getPrix(){
        return this.prix;
    }
    
    public double getMontant(){       
        if(quantite>0 && prix>0)
            montant = quantite*prix;

        return montant;
    }
    
    public void setDesc(String str){
        this.desc = new SimpleStringProperty(str);
    }
    
*/   
}
