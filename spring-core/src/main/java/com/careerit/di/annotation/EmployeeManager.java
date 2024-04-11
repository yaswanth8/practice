package com.careerit.di.annotation;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.support.ClassPathXmlApplicationContext;


@ComponentScan(basePackages = "com.careerit.di.annotation")
public class EmployeeManager {

    public static void main(String[] args) {

        ApplicationContext context=new AnnotationConfigApplicationContext(EmployeeManager.class);
        EmployeeService service=context.getBean("employeeService", EmployeeService.class);

        service.searchName("a");
        service.searchName("ab");

    }
}
