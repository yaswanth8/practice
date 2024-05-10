package com.careerit.di.example;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Data
@Service
@RequiredArgsConstructor
public class UserDataService {


    private final UserDataDao userDataDao;
    public String dbVersion(){

return userDataDao.getDbVersion();
    }
}
