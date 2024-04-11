package com.careerit.jfs.TestingPackage;

import java.util.ArrayList;
import java.util.List;

public class Testclass {

    public static void main(String[] args) {
        List<Integer> a=new ArrayList<>() ;
        a.add(1);
        a.add(7);

        a.forEach(System.out::println);


    }
}
