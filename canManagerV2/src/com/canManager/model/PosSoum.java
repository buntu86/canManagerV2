package com.canManager.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PosSoum {
    private double quantite=0, prix=0, montant=0;
    private StringProperty pos, upos, desc, um, article;
    
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
}
