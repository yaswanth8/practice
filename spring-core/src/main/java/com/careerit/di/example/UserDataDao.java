package com.careerit.di.example;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Data
@Repository
public class UserDataDao {

    @Autowired
    private DbConfiguration dbConfiguration;

    public String getDbVersion(){
        return dbConfiguration.getDbVersion();
    }
}
