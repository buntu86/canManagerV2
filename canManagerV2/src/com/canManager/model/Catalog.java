package com.canManager.model;

import com.canManager.data.ReadDbf;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Catalog {
    
    private static ArrayList<Articles> listArticles = new ArrayList<>();
    private static String title;
    
    public static void setCatalog(String strCatalog) {
        ReadDbf.setDbfFile(strCatalog);
        ReadDbf.iniListArticles();
        listArticles = ReadDbf.getListArticles();
        
        setTitle(Paths.get(strCatalog).getFileName().toString());
    }
    
    public static ArrayList<Articles> getCatalog(){
        return listArticles;
    }
    
    public static ArrayList<Articles> getLevel1(){
        ArrayList<Articles> tmpList = new ArrayList<>();
        
        for(int i=0; i<listArticles.size(); i++)
        {
            if(!tmpList.isEmpty())
            {
                if(tmpList.get(tmpList.size()-1).getPosInt()/100!=listArticles.get(i).getPosInt()/100)
                    tmpList.add(listArticles.get(i));
            }
            else
                tmpList.add(listArticles.get(i));
        }
        
        return tmpList;
    }

    public static ArrayList<Articles> getLevel2(int level1) {
        ArrayList<Articles> tmpList = new ArrayList<>();

        for(int i=0; i<listArticles.size(); i++)
        {
            if(level1/100==listArticles.get(i).getPosInt()/100 & level1!=listArticles.get(i).getPosInt())
            {
                if(!tmpList.isEmpty())
                {
                    if(tmpList.get(tmpList.size()-1).getPosInt()/10!=listArticles.get(i).getPosInt()/10)
                        tmpList.add(listArticles.get(i));
                }
                else
                    tmpList.add(listArticles.get(i));
            }
        }
        return tmpList;
    }

    public static ArrayList<Articles> getLevel3(int level2) {
        ArrayList<Articles> tmpList = new ArrayList<>();

        for(int i=0; i<listArticles.size(); i++)
        {
            if(level2/10==listArticles.get(i).getPosInt()/10 & level2!=listArticles.get(i).getPosInt())
            {
                if(!tmpList.isEmpty())
                {
                    if(tmpList.get(tmpList.size()-1).getPosInt()!=listArticles.get(i).getPosInt())
                        tmpList.add(listArticles.get(i));
                }
                else
                    tmpList.add(listArticles.get(i));
            }
        }
        return tmpList;
    }
    
    public static String getArticle(int pos, int upos, int var){
        String str = new String();
        int loop = 1;

        ArrayList<Articles> artFromStream = listArticles
                .stream()
                .filter(art -> art.getPos().equals(String.format("%03d", pos)) && art.getUpos().equals(String.format("%03d", upos)) && art.getVar().equals(String.format("%02d", var)))
                .collect(Collectors.toCollection(ArrayList::new));
        
        for(Articles tmp : artFromStream)
        {
            if(artFromStream.size()==loop && tmp.getBegin()>0)
                str += tmp.getText().substring(0, tmp.getBegin()-1);
            else
                str += tmp.getText();
                
            loop++;
        }
        
        return str;
    }

    public static void setTitle(String strCatalog) {
        Catalog.title = strCatalog;
    }
    public static String getTitle(){
        return Catalog.title;
    }
}