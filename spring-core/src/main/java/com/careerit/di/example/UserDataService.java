package com.careerit.di.example;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@Service
public class UserDataService {

    @Autowired
    private UserDataDao userDataDao;
    public String dbVersion(){

return userDataDao.getDbVersion();
    }
}
