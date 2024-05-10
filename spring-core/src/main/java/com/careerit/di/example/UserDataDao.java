package com.careerit.di.example;


import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Data
@Repository
@RequiredArgsConstructor
public class UserDataDao {


    private final DbConfiguration dbConfiguration;

    public String getDbVersion(){
        return dbConfiguration.getDbVersion();
    }
}
