package com.careerit.jfs.day1;

import java.util.Scanner;

public class HelloWorld {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int sum=0;
        for (int i = 1; i <= n; i++) {

            if(i%3==0 && i%5==0)
                System.out.println("Fizz & Buzz");
            else if (i%3==0)
                System.out.println("Fizz");
            else if (i%5==0)
                System.out.println("Buzz");
            else {
                System.out.println(i);
                sum=sum+i;
                if(sum>(3*n)){
                    break;
                }
            }
        }


    }
}
