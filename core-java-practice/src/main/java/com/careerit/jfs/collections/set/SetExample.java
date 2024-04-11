package com.careerit.jfs.collections.set;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetExample {
    public static void main(String[] args) {

        Set<String> set=new HashSet<>();
        set.add("ravi");
        set.add("krish");
        set.add("ash");
        System.out.println(set);
        Set<Integer> set1=new HashSet<>();
        set1.add(1);
        set1.add(1);
        System.out.println(set1);

        for(String ele:set){
            System.out.println(ele);
        }

        // java 8
        set.forEach(e -> System.out.println(e));
        set.forEach((System.out::println));

        Iterator<String> iterator= set.iterator();
        while(iterator.hasNext()){
            String value= iterator.next();
            System.out.println(value);
        }

    }
}
