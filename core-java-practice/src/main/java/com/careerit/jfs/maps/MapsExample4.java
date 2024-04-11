package com.careerit.jfs.maps;

import java.util.*;

public class MapsExample4 {

    public static void main(String[] args) {

        Map<String,Integer> m=new HashMap<>();

        m.put("a",1);
        m.put("b",2);
        m.get("a");
        Collection<Integer> x= m.values();

        System.out.println(x);
    }
}
