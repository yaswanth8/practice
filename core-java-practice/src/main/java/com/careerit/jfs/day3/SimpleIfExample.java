package com.careerit.jfs.day3;

import java.util.Scanner;

public class SimpleIfExample {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the number");
        int num = sc.nextInt();


            int c_sum = 0;
            for (int i = 1; i > 0 && i<=num; i++) {
                int c_rem = i % 10;
                c_sum = c_sum + c_rem;
                System.out.println(c_sum);
                i = i / 10;
            }


        System.out.println(num);
    }
}
