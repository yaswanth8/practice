package com.careerit.jfs.oops.inheritance;

class C1{

    public C1(){
        System.out.println("c1");
    }
    public void show(){
        System.out.println("show1");
    }
}
class C2 extends C1{
    public C2(){
        System.out.println("c2");
    }
    public void show(){
        System.out.println("show2");
    }
}
class C3 extends C2{
    public C3(){
        System.out.println("c3");
    }
    public void show(){
        System.out.println("show3");
    }
}
public class SuperKeyWord {
    public static void main(String[] args) {
        C1 obj1=new C3() ;
        obj1.show();
    }
}
