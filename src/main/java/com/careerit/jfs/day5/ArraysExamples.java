package com.careerit.jfs.day5;

import java.util.Scanner;

public class ArraysExamples {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);

        int[] arr=new int[100];

        // taking input
        int N=sc.nextInt();

        for(int i=0;i<N;i++)
            arr[i] = sc.nextInt();

        display(arr,N);

    }

    // display method

    public static void display(int[] arr,int N){

        for(int i=0;i<N;i++)
            System.out.print(arr[i]+" ");

    }

}
