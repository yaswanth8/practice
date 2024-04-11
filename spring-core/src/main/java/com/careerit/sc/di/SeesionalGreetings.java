package com.careerit.sc.di;

public class SeesionalGreetings implements GreetingService{


    @Override
    public String greet(String name) {

        return String.format("Dear %s, wish season greetings ",name);
    }
}
