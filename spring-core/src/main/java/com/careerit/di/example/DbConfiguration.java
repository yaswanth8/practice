package com.careerit.di.example;

import org.springframework.stereotype.Component;

@Component
public class DbConfiguration {


    public String getDbVersion(){
        return "V1.0";
    }
}
