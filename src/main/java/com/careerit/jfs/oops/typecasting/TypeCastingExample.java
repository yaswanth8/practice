package com.careerit.jfs.oops.typecasting;

public class TypeCastingExample {

    public static void main(String[] args) {
        int a=10;
        double b=a; // implicit type casting or winding


        double c=10.3;
        int d= (int) c; // explicit type casting or narrowing


        String str="234";
        int num=Integer.parseInt(str);

        // byte,short,int,long,float,double,char,boolean

        // Boxing and unboxing
        int x=10;
        Integer obj=x; // boxing
        int y=obj; // unboxing


    }
}
