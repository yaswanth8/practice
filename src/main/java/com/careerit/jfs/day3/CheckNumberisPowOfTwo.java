package com.careerit.jfs.day3;

import java.util.Scanner;

public class CheckNumberisPowOfTwo {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int num=sc.nextInt();
        int count=0;
        while(num%2==0){
            num/=2;
            count++;
            if(num==1)
                System.out.println("Yes and the 2 power is "+count);

        }
    }
}
