package com.careerit.di.scope;


import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("prototype")
public class TaskManager {

    public List<String> showActiveTask(){
        return List.of("Task1","Task2","Task3");
    }
}
