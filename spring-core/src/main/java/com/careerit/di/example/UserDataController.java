package com.careerit.di.example;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Data
@Component
@RequiredArgsConstructor
public class UserDataController {


    private final UserDataService userDataService;

    public String getDbVersion(){
        return userDataService.dbVersion();
    }


}


