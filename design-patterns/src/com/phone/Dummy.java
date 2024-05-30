package com.phone;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Dummy {

    public static void main(String[] args) {

        List<String> names=List.of("yaswanth","nag","vamsi","prasanth");
        List<String> list = names.stream().filter(name -> name.startsWith("y"))
                .map(name -> name.toUpperCase())
                .collect(Collectors.toList());

        System.out.println(list);


        Function<Integer,Boolean>   isOdd= x -> x%2!=0;

        boolean a=isOdd.apply(3);



        System.out.println(a);
        List<Integer>   list2=List.of(1,2,3,4,5,6,7,8,9,10);

        List<Integer> list3=list2.stream().map(x ->x*3).filter(x->x>8).filter(x->x%2==0).collect(Collectors.toList());
        System.out.println(list3);



    }
}
