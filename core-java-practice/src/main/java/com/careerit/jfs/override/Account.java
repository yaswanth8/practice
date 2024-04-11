package com.careerit.jfs.override;

public class Account {

    private int accNum;
    private String accName;
    private double bal;

    public Account(int accNum,String accName,double bal){
        this.accNum=accNum;
        this.accName=accName;
        this.bal=bal;
    }

    public void showBalance(){
        System.out.println("Bal is :"+bal);
    }

}
