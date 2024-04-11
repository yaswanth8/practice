package com.careerit.jfs.TestingPackage;

import java.util.ArrayList;
import java.util.List;

public class ListWithStreamExample {

    public static void main(String[] args) {
        List<Integer> list=getRandomNumbers(20);
        System.out.println(list);
        List<Integer> doubleList=new ArrayList<>();
        for(int ele:list){
            doubleList.add(ele*2);
        }
        List<Integer> list1 = list.stream().filter(e ->e%2==0).toList();
        List<Integer> list3=list.stream()
                        .filter(ele-> ele >30 && ele<90).toList();
        System.out.println(list3);
        List<String> names=List.of("ravi","mahesh","suresh","ganesh");
        List<Integer> nameLength=new ArrayList<>();

        for(String name:names){
            nameLength.add(name.length());
        }

    }

    private static List<Integer> getRandomNumbers(int n) {
    List<Integer> list=new ArrayList<>();
    for(int i=1;i<=n;i++){
            ;
        list.add((int) (Math.random()*100));
    }
    return list;
    }
}
