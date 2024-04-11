package com.careerit.jfs.day3;

public class SumOfDigitsInRange {
    public static void main(String[] args) {

        for(int l=5095,r=5105;l<=r;l++){
            int i=l;
            int sum=0;
            for(;i>0;i/=10){
                int rem=i%10;
                sum=sum+rem;
            }
            System.out.println("sum of digits "+l+" is "+sum);
        }
    }
}
