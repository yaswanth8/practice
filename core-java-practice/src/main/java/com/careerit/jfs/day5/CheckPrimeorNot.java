package com.careerit.jfs.day5;

import java.util.Scanner;

public class CheckPrimeorNot {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        System.out.println(primeOrNot(N));
    }

     public static boolean primeOrNot(int N){
        if(N==1 ||N==0)
            return false;
        for(int i=2;i<=Math.sqrt(N);i++){
            if(N%i==0){
                return false;
            }
        }
        return true;
    }
}
