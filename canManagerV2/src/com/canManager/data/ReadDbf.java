package com.canManager.data;

import com.canManager.utils.Log;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.Alert;

public class ReadDbf {

    private static Path dbfFile = null;
    private static ArrayList<Articles> listArticles = new ArrayList<>();
    private static byte[] data = null;
    private static String s = null;
    private static int start = 0;
    
    protected static ArrayList<Articles> getListArticles(){
        
        if(listArticles.isEmpty())
        {
            importListArticlesFromDbf();
            Log.msg(0, "importListArticlesFromDBF");
            simplification();
            Log.msg(0, "simplification");
        }

        return listArticles;
    }
    
    private static void importListArticlesFromDbf()
    {        
        Charset stringCharset = Charset.forName("IBM437");
        DbfHeader header = new DbfHeader(dbfFile);           
        try {
            data = Files.readAllBytes(header.getPathDbfFile());
            s = new String(data, "IBM437");
            start=header.getNumHeader()+1;
            
            for(int j=0; j<header.getNumRecordsTable(); j++)
            {                
                int i8=Character.getNumericValue(s.charAt(start+8)), 
                        i9=Character.getNumericValue(s.charAt(start+9)), 
                        i15=Character.getNumericValue(s.charAt(start+15)), 
                        i16=Character.getNumericValue(s.charAt(start+16));
                
                if(i8<0)
                    i8=0;
                if(i9<0)
                    i9=0;
                if(i15<0)
                    i15=0;
                if(i16<0)
                    i16=0;
                
                String pos = s.substring(start+0, start+2);
                System.out.println(pos + "|" + s.substring(start+0, start+2));
                String upos = s.substring(start+3, start+5);
                String var = s.substring(start+6, start+7);
                
                int line = i8*10 + i9;
                
                String alt = Character.toString(s.charAt(start+10));
                String unit = s.substring(start+11, start+12);
                
                String pub = s.substring(start+13, start+14);
                
                int begin = i15*10 + i16;
                
                String text = s.substring(start+17, start+77).trim();
                
                listArticles.add(new Articles(pos, upos, var, line, alt, unit, pub, begin, text));
                
                
                start+=header.getNumRecord();
                
            }
            
        } catch(Exception e){
            System.out.println("[ X ] " + e.getMessage());
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DbfBody");
            alert.setHeaderText(null);
            alert.setContentText(header.getPathDbfFile().toString() + "\nErreur lors de l'ouverture du fichier\nVeuillez choisir un autre nom de fichier et réessayer.\nErreur : " + e.getMessage());
            alert.showAndWait();       
        }         
    }
    
    public static boolean setDbfFile(String str){
        dbfFile = Paths.get(str);
        if(Files.exists(dbfFile))
        {
            Log.msg(0, "Le fichier dbf existe.");
            return true;
        }
        else
        {
            Log.msg(1, "Le fichier dbf n'existe pas.");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Convertir");
            alert.setHeaderText(null);
            alert.setContentText("Le fichier \"" + dbfFile.toString() + "\" n'existe pas.");
            alert.showAndWait();
            return false;
        }
    }

    private static void simplification() {
        ArrayList<Articles> tmpList = new ArrayList<>();
        
        for(int i=0; i<listArticles.size(); i++){
           if(!tmpList.isEmpty())
           {
               if(listArticles.get(i).getPos().equals(tmpList.get(tmpList.size()-1).getPos()) & 
                       listArticles.get(i).getUpos().equals(tmpList.get(tmpList.size()-1).getUpos()) & 
                       listArticles.get(i).getVar().equals(tmpList.get(tmpList.size()-1).getVar()))
                   tmpList.get(tmpList.size()-1).simplificationText(listArticles.get(i));
                   
               else
               {
                   tmpList.add(listArticles.get(i));
               }
           }
           else
               tmpList.add(listArticles.get(i));
        }
        listArticles=tmpList;
    }
}
