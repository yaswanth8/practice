package com.careerit.jfs.maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class MapExample3 {

    public static void main(String[] args) {
        Map<String,Integer> map=new HashMap<>();

        map.put("Krish",1001);
        map.put("Jhon",1002);
        map.put("Ravi",1003);

        Set<String> keys=map.keySet();

        for(String s:keys){
            System.out.println(s+" : "+map.get(s));
        }
    }
}
