package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ANNULERListPositionsSoum {
    private ArrayList<PosSoum> positionsSoum = new ArrayList<>();
    private List<String> list = null;
    
    ANNULERListPositionsSoum(CatalogFile catFile) {
        
        if(catFile!=null){
            Catalog.setCatalog(catFile.getPath().toString());
            list = ReadSoum.getRawData().stream()
                    .filter(line -> line.startsWith("G"+catFile.getNum()) && line.substring(41,42).equals("2"))
                    .collect(Collectors.toList());

            for(String element : list)
            {
                positionsSoum.add(new PosSoum(element, catFile));
            }
            Log.msg(0, "constructor " + catFile.getNum() + "." + catFile.getAnnee() + " : nbrPositions | " + list.size());
        }
    }

    public ArrayList<PosSoum> get(){
        Log.msg(0, "ListPositionsSoum.get()");
        return positionsSoum;
    }    
}