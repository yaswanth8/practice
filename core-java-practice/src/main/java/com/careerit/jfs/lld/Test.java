package com.careerit.jfs.lld;


public class Test {

    public static void main(String[] args) {
        Car car=new Car();

        System.out.println(car.getColor());

    }
}


class Car{
    private int speed;
    private String color;

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}