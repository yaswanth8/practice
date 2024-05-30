package com.phone;

public class Shop {

    public static void main(String[] args) {

        Phone p=new PhoneBuilder().setBattery(100).getPhone();

        System.out.println(p);

    }
}
