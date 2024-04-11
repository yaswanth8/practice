package com.careerit.jfs.oops;

public class AccountManager {

    public static void main(String[] args) {

        Account acc1=new Account("Ram",1000);
        Account acc2=new Account("Sita",5000);

        acc1.withdrawAmount(5000);
        acc1.showBalance();
        acc2.depositAmount(1000);
        acc2.showBalance();
        int a = Account.test;
    }
}
