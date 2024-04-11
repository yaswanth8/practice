package com.careerit.jfs.day5;

import java.util.Scanner;

public class ShotestOfArr {

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        int N=sc.nextInt();
        int[] arr=new int[N];
        for(int i=0;i<N;i++){
            int val=sc.nextInt();
            arr[i]=val;
        }
        int min=arr[0];
        for(int i=1;i<N;i++){
            if(min>arr[i]){
                min=arr[i];
            }

        }
        System.out.println(min);
    }
}
