package com.careerit.jfs.oops.statickeyword;

public class staticblockexample {
    static {
        System.out.println(" Static block");
    }

    public static void main(String[] args) {
        System.out.println("Main block");
    }

    static {
        System.out.println(" S b 2");
    }
    // called only one when the class is loaded into memory
    // static is called before main method
    // static blocks are called in the order they are defined
    // they cannot called explicity


}
