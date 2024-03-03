package com.careerit.jfs.override;

import java.util.List;

public class AccountManager {
    public static void main(String[] args) {
        Account acc=new Account(1,"Ram",100.2);
        acc.showBalance();
    }
    List<Account> getAccount(){
        return List.of(new Account(1,"as",122));
    }
}
