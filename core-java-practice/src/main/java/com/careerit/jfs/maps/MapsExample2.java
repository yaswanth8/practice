package com.careerit.jfs.maps;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MapsExample2 {

    public static void main(String[] args) {
         String a="learning java or java have learning fun is fun";

         String[] arr=a.split(" ");

        Map<String,Integer> wordCount=new HashMap<>();

        for(String word:arr) {
            wordCount.put(word,wordCount.getOrDefault(word,0)+1);
        }

        System.out.println(wordCount);

    }
}
