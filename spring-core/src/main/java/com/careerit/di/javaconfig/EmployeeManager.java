package com.careerit.di.javaconfig;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;



public class EmployeeManager {

    public static void main(String[] args) {

        ApplicationContext context=new AnnotationConfigApplicationContext(EmpConfig.class);
        EmployeeService service=context.getBean("employeeService", EmployeeService.class);

        service.searchName("b");
        service.searchName("ab");

    }
}
