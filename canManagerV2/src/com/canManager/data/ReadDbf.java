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
            start=header.getNumHeader()+1;
            
            for(int j=0; j<header.getNumRecordsTable(); j++)
            {                
                String pos = getStr(0, 2);
                String upos = getStr(3, 5);
                String var = getStr(6, 7);
                
                int line = (char)data[start+8]*10 + (char)data[start+9];
                
                String alt = getStr(10, 10);
                String unit = getStr(11, 12);
                
                String pub = getStr(13, 14);
                int begin = (char)data[start+15]*10 + (char)data[start+16];
                
                String text = new String(getStr(17, 77).getBytes(), "IBM437");

                for(int w=17; w<=77; w++)
                {
                    if(data[start+w]<0)
                        System.out.println(data[start+w] + " | " + text);
                }
                //text = new String(text.getBytes("IBM437"));
                //https://www.ibm.com/support/knowledgecenter/en/SSMKHH_9.0.0/com.ibm.etools.mft.doc/ac00408_.htm
                //https://fr.wikipedia.org/wiki/Page_de_code_437

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
        //http://stackoverflow.com/questions/30776625/append-byte-after-byte-array
        List<Byte> tmpByte = new ArrayList<>();
        
        for(int i=i1; i<=i2; i++)
        {
            tmpByte.add(data[start+i]);
            /*
            int tmp=0;
            
            if(data[start+i]<0)
               tmp=data[start+i];
            else
                tmp=data[start+i];
            str = str + String.valueOf((char)tmp);
            
            //str += String.valueOf((char)data[start+i]);*/
        }
        
        //byte[] tmpByte2 = tmpByte.toArray(new Byte[tmpByte.size()]);
        
        
        return str;
    }

/*
    private static String getStr(int i1, int i2) {
        String str = new String();
        for(int i=i1; i<=i2; i++)
            str = str + String.valueOf((char)data[start + i]);
        
        return str;
    }    
    */    
    
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
