package com.canManager.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Articles {
    
    private IntegerProperty  line, begin;
    private StringProperty alt, unit, text, pos, upos, var, pub;

    public Articles(String pos, String upos, String var, int line, String alt, String unit, String pub, int begin, String text){
        if(pos.length()==2)
            pos=0+pos;
        
        this.pos = new SimpleStringProperty(pos);
        this.upos = new SimpleStringProperty(upos);
        this.var = new SimpleStringProperty(var);
        this.line = new SimpleIntegerProperty(line);
        this.alt = new SimpleStringProperty(alt);
        this.unit = new SimpleStringProperty(unit);
        this.pub = new SimpleStringProperty(pub);
        this.begin = new SimpleIntegerProperty(begin);
        this.text = new SimpleStringProperty(text);
    }

    //GET
    public String getPos(){
        return pos.get();
    }
    public int getPosInt(){
        return Integer.parseInt(pos.get());
    }
    public String getUpos(){
        return upos.get();
    }
    public int getUPosInt(){
        return Integer.parseInt(upos.get());
    }    
    public String getVar(){
        return var.get();
    }
    public int getVarInt(){
        return Integer.parseInt(var.get());
    }
    public int getLine() {
        return line.get();
    }
    public String getAlt() {
        return alt.get();
    }
    public String getUnit() {
        return unit.get();
    }
    public String getPub() {
        return pub.get();
    }
    public int getBegin() {
        return begin.get();
    }
    public String getText() {
        return text.get();
    }
    public String getTextTitle() {     
        String tmp = text.get().replaceAll("\n", " ");
        tmp = tmp.replaceAll("- ", "");
        
        if(tmp.contains("----"))
            tmp = tmp.substring(0, tmp.indexOf("----"));

        return tmp;
    }
    
    //SET
    public void simplificationText(Articles tmpArt) {
        this.line.set(tmpArt.getLine());
        this.begin.set(tmpArt.getBegin());
        this.text.set(this.text.get() + "\n" +  tmpArt.getText());
    }
}
