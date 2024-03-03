package com.careerit.jfs.day5;

import java.util.Scanner;

public class FirstNnumbers {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int N = sc.nextInt();
        int K = sc.nextInt();
        int count = 0;
        int sum = 0;

        for (int i = 1; count <= N; i++) {

            int rem = i % 10;
            sum += rem;
            if (sum == K) {
                System.out.println(i);
                count++;
            }


        }
    }
}
