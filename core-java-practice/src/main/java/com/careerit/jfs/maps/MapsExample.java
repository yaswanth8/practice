package com.careerit.jfs.maps;

import java.util.HashMap;
import java.util.Map;

public class MapsExample {

    public static void main(String[] args) {
        Map<String,Integer> map=new HashMap<>();

        map.put("Krish",1001);
        map.put("Jhon",1002);
        map.put("Ravi",1003);
        map.putIfAbsent("Krish",1005);
        System.out.println(map.get("Krish"));
        System.out.println(map.keySet());

        if(map.get("Jhon")!=null){
            map.put("Jhon",map.get("Jhon")+2);
            System.out.println(map.get("Jhon"));
        }
    }
}
