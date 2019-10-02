package com.canManager.data;

import com.canManager.model.Articles;
import com.canManager.model.Catalog;
import com.canManager.utils.Log;

public class ExportToCsv {
    
    public ExportToCsv(String strFile){
        Log.msg(0, strFile);
        Catalog.setCatalog(strFile);
        
        for(Articles artLev1 : Catalog.getLevel1()){
            Log.msg(1, artLev1.getPos() + " - " + artLev1.getTextTitle());
            
            for(Articles artLev2 : Catalog.getLevel2(artLev1.getPosInt())){
                Log.msg(1, artLev2.getPos() + " - " + artLev2.getTextTitle());
            
                for(Articles artLev3 : Catalog.getLevel3(artLev2.getPosInt())){
                    Log.msg(1, artLev3.getPos() + " - " + artLev3.getTextTitle());
                }
            }
        }
    }
}
