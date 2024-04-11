package com.careerit.jfs.oops.statickeyword;

public class Employee {

    private int empno;
    private String name;
    private double salary;
    private String email;
    private String mobile;
    public static int count=0;

    public Employee(int empno,String name,double salary){
            this(empno,name,salary,"NA","NA");

    }

    public Employee(int empno, String name, double salary, String email, String mobile) {
        this.empno = empno;
        this.name = name;
        this.salary = salary;
        this.email = email;
        this.mobile = mobile;

    }

    {
        count++;
    }

    public static void main(String[] args) {
        System.out.println(" Total emp: "+count);
        Employee obj=new Employee(101,"Yaswanth",100000);
        System.out.println("Total emp: "+count);
    }
}
