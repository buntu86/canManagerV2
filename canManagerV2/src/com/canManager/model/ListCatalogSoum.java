package com.canManager.model;

import com.canManager.data.ReadSoum;
import com.canManager.utils.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListCatalogSoum {
    private ArrayList<CatalogSoum> catalogSoum = new ArrayList<>();

    ListCatalogSoum() {
        List<String> list = ReadSoum.getRawData().stream()
                .filter(line -> line.startsWith("G") && line.substring(41,42).equals("1"))
                .collect(Collectors.toList());
        
        Log.msg(0, "constructor : nbrCatalog " + list.size());
        for(String element : list)
        {
            catalogSoum.add(new CatalogSoum(element));
        }
    }
    
    public ArrayList<CatalogSoum> get(){
        Log.msg(0, "ListCatalogSoum.get()");
        return catalogSoum;
    }
}
