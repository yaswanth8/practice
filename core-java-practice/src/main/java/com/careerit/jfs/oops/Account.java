package com.careerit.jfs.oops;

public class Account {
    private long accNum;

    private String name;

    private double balance;

    public static int test=5;

    public Account(String name, double balance) {
        accNum = System.currentTimeMillis();
        this.name = name;
        this.balance = balance;
    }

    public void withdrawAmount(double amount){

        if(balance<amount){
            System.out.println("Insufficient balance");
        }
        else{
            balance-=amount;
            System.out.println("Your account num "+accNum+"has been debited with amount "+amount);
            System.out.println("Update balance is "+balance);
        }

    }

    public void depositAmount(double amount){
        balance+=amount;
        System.out.println("amount credited "+amount);
        System.out.println("Updated balance : "+balance);
    }

    public void showBalance(){
        System.out.println(" Current balance "+balance);
    }
}
