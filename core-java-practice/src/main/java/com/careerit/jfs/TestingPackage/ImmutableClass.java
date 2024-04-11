package com.careerit.jfs.TestingPackage;

import lombok.ToString;

@ToString
public final class ImmutableClass {
    private final int value;
    private final String name;

    public ImmutableClass(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static void main(String[] args) {

        ImmutableClass obj=new ImmutableClass(2,"a");
        System.out.println(obj);
    }
}

