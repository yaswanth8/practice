package com.careerit.jfs.oops.inheritance;

public class InheritanceExample2 {

    public static void main(String[] args) {
        T1 obj1=new T2();
        obj1.show1();


    }
}

class T1 {
    public T1(){
        System.out.println("T1 const");
    }
public void show1(){
    System.out.println("Show 1");
}
}
class T2 extends T1{
    public T2(){
        System.out.println("T2 const");
    }
    public void show2(){
        System.out.println("Show 2");
    }

}

