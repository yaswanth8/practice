package com.careerit.jfs.collections.set;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SetExample2 {

    public static void main(String[] args) {
        List<String> cs=List.of("Krish","Ravi","Ramya");
        List<String> ba=List.of("Krish","Syam","Ramya");
        List<String> it=List.of("Krish","Ravi","Ramya");

        Set<String> un=new HashSet<>(cs);

        un.addAll(ba);
        un.addAll(it);
        System.out.println(un);



    }
}
