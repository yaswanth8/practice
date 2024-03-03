package com.careerit.jfs.ExceptionsPackage;

import java.util.Scanner;

public class ExampleExp1 {

    public static void main(String[] args) {

        System.out.println("Start main");
        Scanner sc=new Scanner(System.in);
        int a =sc.nextInt();
        int b =sc.nextInt();
        int res=divide(a,b);
        System.out.println("Result : "+res);
        System.out.println("End of main");
    }

    private static int divide(int a,int b){
        int res=0;
        try{
            res=a/b;
        }catch(Exception e){
            System.out.println(" cant' divide by zero");
            e.printStackTrace();

        }finally {

        }
        return res;
    }

}
