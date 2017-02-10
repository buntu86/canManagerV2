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
    private static final ArrayList<Articles> listArticles = new ArrayList<>();
    private static byte[] data = null;
    private static int start = 0;
    
    public static List<Articles> getListArticles(){
        
        if(listArticles.isEmpty())
        {
            importListArticlesFromDbf();
            Log.msg(0, "importListArticlesFromDBF");
        }

        return listArticles;
    }
    
    private static void importListArticlesFromDbf()
    {        
        DbfHeader header = new DbfHeader(dbfFile);           
        try {
            data = Files.readAllBytes(header.getPathDbfFile());
            start=header.getNumHeader()+1;
            
            for(int j=0; j<header.getNumRecordsTable(); j++)
            {                
                String pos = getStr(0, 2);
                String upos = getStr(3, 5);
                String var = getStr(6, 7);
                
                int line = (char)data[start+8]*10 + (char)data[start+9];
                
                String alt = getStr(10, 10);
                String unit = getStr(11, 12);
                
                int pub = (char)data[start+13]*10 + (char)data[start+14];
                int begin = (char)data[start+15]*10 + (char)data[start+16];
                
                String text = getStr(17, 77);

                /*byte[] tmpByte = tmpString.getBytes("IBM437");
                String text = new String(tmpByte);*/
                
                System.out.println(pos + "." + upos + " " + var + " " + text);

                listArticles.add(new Articles(pos, upos, var, line, alt, unit, pub, begin, text.trim()));
                
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

    private static String getStr(int i1, int i2) {
        String str = new String();
        for(int i=i1; i<=i2; i++)
        {
            //System.out.print(String.valueOf((char)data[start + i]));
            str = str + String.valueOf((char)data[start + i]);
        }
        //System.out.print("|");
        
        return str;
    }
}
