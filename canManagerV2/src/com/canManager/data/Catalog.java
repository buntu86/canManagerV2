package com.canManager.data;

import com.canManager.utils.Log;
import java.util.ArrayList;

public class Catalog {
    
    private static ArrayList<Articles> listArticles = new ArrayList<>();
    private static String title;
    
    public static void setCatalog(String strCatalog) {
        ReadDbf.setDbfFile("src/com/canManager/ressources/" + strCatalog + ".dbf");
        listArticles = ReadDbf.getListArticles();
        Log.msg(0, "setCatalog");
        setTitle(strCatalog);
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

    public static void setTitle(String strCatalog) {
        Catalog.title = strCatalog;
    }
    public static String getTitle(){
        return Catalog.title;
    }
}