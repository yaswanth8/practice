package com.careerit.sc.di;

public class BirthdayGreetings implements GreetingService{


    @Override
    public String greet(String name) {
        return String.format(" Dear %s, Wish you a very happy birthday",name);

    }
}
