package com.careerit.jfs.TestingPackage;

import java.util.Iterator;
import java.util.List;

public class Dummyclass {

    public static void main(String[] args) {
        ImmutableClass obj=new ImmutableClass(1,"a");
        System.out.println(obj);

        List<Integer> list= List.of(1,2,3,4,5);
        list.forEach((e)-> {
            if(e%2==0)
                System.out.println(e);

        });

        for(int i:list){
            System.out.println(i);
        }
        Iterator<Integer> itr=list.iterator();
        System.out.println("Intertor");
          while(itr.hasNext()){
            int e=itr.next();
            System.out.println(e);
        }
        System.out.println("-".repeat(3));

          itr.forEachRemaining(e-> System.out.println(e*2));
    }

    public void x(){
        System.out.println("asd");
    }

    public void y(){
        System.out.println("y");
        x();
    }
}
