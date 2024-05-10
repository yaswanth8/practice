package com.careerit.di.scope;


import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan (basePackages = "com.careerit.di.scope")
public class Manager {

    public static void main(String[] args) {
        ApplicationContext context=new AnnotationConfigApplicationContext(Manager.class);


        TaskManager taskManager1=context.getBean(TaskManager.class);
        TaskManager taskManager2=context.getBean(TaskManager.class);
        TaskManager taskManager3=context.getBean(TaskManager.class);

        System.out.println(taskManager1);
        System.out.println(taskManager2);
        System.out.println(taskManager3);
    }
}
