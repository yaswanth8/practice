package com.careerit.jfs.oops.abstractexamples;


import java.util.Scanner;

abstract class Game{

    public void start(){
        System.out.println("Start game");
    }
    public abstract void play();
    public void end(){
        System.out.println("end game");
    }
}

class Bike extends Game{
    @Override
    public void play(){
        System.out.println(" Class name "+this.getClass().getSimpleName()+" slow");
    }
}
class Car extends Game{
    @Override
    public void play(){
        System.out.println(" Class name "+this.getClass().getSimpleName()+" steer slow");
    }
}

class Ship extends Game{
    @Override
    public void play(){
        System.out.println(" Class name "+this.getClass().getSimpleName()+" water");
    }
}
public class GameManager {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println(" Enter the game 1. Bike 2. Car 3. Ship 4. Exit");

            int a = sc.nextInt();
            if (a == 4) {
                System.exit(0);
            }

            Game gm =  getGame(a);
            gm.start();
            gm.play();
            gm.end();


        }
    }

        public static Game getGame(int ch)  {
            return switch (ch) {
                case 1 -> new Bike();
                case 2 -> new Car();
                case 3 -> new Ship();
                default -> throw new IllegalArgumentException(" Invalid ");
            };
        }


}
