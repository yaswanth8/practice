package com.careerit.di.example;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@ComponentScan(basePackages = "com.careerit.di.example")
public class UserDataManager {

    public static void main(String[] args) {
        ApplicationContext context=new AnnotationConfigApplicationContext(UserDataManager.class);
        UserDataController controller=context.getBean("userDataController",UserDataController.class);
        System.out.println(controller.getDbVersion());
    }
}
