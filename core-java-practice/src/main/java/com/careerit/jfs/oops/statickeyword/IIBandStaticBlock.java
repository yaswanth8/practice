package com.careerit.jfs.oops.statickeyword;

import com.sun.tools.javac.Main;

public class IIBandStaticBlock {

    {
        System.out.println("IIB block");
    }
    static {
        System.out.println("Static");
    }

    public static void main(String[] args) {
        System.out.println("Main");
        IIBandStaticBlock obj=new IIBandStaticBlock();
        IIBandStaticBlock obj2=new IIBandStaticBlock();

    }
}

class C{
    int a;
    static C b;

    public static void main(String[] args) {
        System.out.println(C.b);
    }
}
