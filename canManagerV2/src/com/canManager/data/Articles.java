package com.canManager.data;

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
    public String getVar(){
        return var.get();
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
    /*
    //PROPERTY
    public IntegerProperty positionProperty(){
        return position;
    }
    public IntegerProperty subPositionProperty(){
        return subPosition;
    }    
    public IntegerProperty variableProperty(){
        return variable;
    } 
    public IntegerProperty lineProperty(){
        return line;
    }
    public StringProperty altProperty(){
        return alt;
    }       
    public StringProperty unitProperty(){
        return unit;
    }        
    public IntegerProperty publicationProperty(){
        return publication;
    }        
    public IntegerProperty beginProperty(){
        return begin;
    }        
    public StringProperty textProperty(){
        return text;
    }      
}
/*

    public CatalogArticles() {
        this(0, 0, 0, 0, 0, "", "", 0, 0, "");
    }
    

    
    //SET
    public void setID(int ID){
        this.ID.set(ID);
    }
    public void setPosition(int position){
        this.position.set(position);
    }
    public void setSubPosition(int subPosition){
        this.subPosition.set(subPosition);
    }
    public void setVariable(int variable){
        this.variable.set(variable);
    }
    public void setLine(int line){
        this.line.set(line);
    }
    public void setAlt(String alt){
        this.alt.set(alt);
    }
    public void setUnit(String unit){
        this.unit.set(unit);
    }
    public void setPublication(int publication){
        this.publication.set(publication);
    }
    public void setBegin(int begin){
        this.begin.set(begin);
    }
    public void setText(String text){
        this.text.set(text);
    }
    public void setArticle(int ID, int position, int subPosition, int variable, int line, String alt, String unit, int publication, int begin, String text){
        this.ID = new SimpleIntegerProperty(ID);
        this.position = new SimpleIntegerProperty(position);
        this.subPosition = new SimpleIntegerProperty(subPosition);
        this.variable = new SimpleIntegerProperty(variable);
        this.line = new SimpleIntegerProperty(line);
        this.alt = new SimpleStringProperty(alt);
        this.unit = new SimpleStringProperty(unit);
        this.publication = new SimpleIntegerProperty(publication);
        this.begin = new SimpleIntegerProperty(begin);
        this.text = new SimpleStringProperty(text);
    }    
    

    
    @Override
    public String toString() {
        String add = new String();
        
        if(this.getPosition()<100)
            if(this.getPosition()<10)
                add = "00";
            else
                add = "0";
        return add + this.getPosition() + " - " + this.getText();
    }
    
    //UPDATE
    public void updateArticleText(String text){
        this.text.set(this.getText() + "\n" + text);
    }
}
*/