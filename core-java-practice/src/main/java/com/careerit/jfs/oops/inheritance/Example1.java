package com.careerit.jfs.oops.inheritance;

class One{
    public void show1(){
        System.out.println("SHow1");
    }

}
class Two extends One{
    public void show2(){
        System.out.println("Show2");
    }

}
public class Example1 {
    public static void main(String[] args) {
        Two obj=new Two();
        obj.show1();

    }
}
