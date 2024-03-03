package com.careerit.jfs.oops.statickeyword;
import java.io.PrintStream;
public class staticExample2 {
    public static void main(String[] args) {
        Llw.my.println("hi");
    }
}
class Llw{
    public static PrintStream my=System.out;
}
