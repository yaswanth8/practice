package com.careerit.jfs.oops.InterfaceExamples;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

interface MathOperations{
    int perform(int a,int b);
}
interface ArthmaticOperation{
    int addtion(int a,int b,int c);
}

class IntegerMathOperations{
    private MathOperations mathOperations;

    public IntegerMathOperations(MathOperations mathOperations){
        this.mathOperations=mathOperations;
    }
    public int performOperation(int a,int b){
        return mathOperations.perform(a,b);
    }
}
public class InnerClassManager {

    public static void main(String[] args) {


        MathOperations obj=(a,b) -> a+b;
        ArthmaticOperation obj1=(a,b,c) -> Math.max(a,Math.max(b,c));
        IntegerMathOperations isMathOperations=new IntegerMathOperations(obj);
        int res=isMathOperations.performOperation(10,20);
        System.out.println("Result: "+res);
        List<Integer> a= filterEvenNumbers(List.of(1,2,3,4,5,6,7,8,9,10),(s)->(s%2)==0);
        System.out.println(a);

    }

    private List<String> filterNames(List<String> list, Predicate<String> predicate){
        List<String> flist=new ArrayList<>();
        for(String name:list){
            if(predicate.test(name)){
                flist.add(name);
            }
        }
        return  flist;
    }
    private static List<Integer> filterEvenNumbers(List<Integer> list,Predicate<Integer> predicate){
        List<Integer> flist=new ArrayList<>();
        for(int x:flist){
            if(predicate.test(x)){
                flist.add(x);
                // new
            }
        }
        return flist;
    }
}
