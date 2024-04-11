package com.careerit.di.example;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
public class UserDataController {

    @Autowired
    private UserDataService userDataService;

    public String getDbVersion(){
        return userDataService.dbVersion();
    }


}


