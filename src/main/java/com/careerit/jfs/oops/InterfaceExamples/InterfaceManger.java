package com.careerit.jfs.oops.InterfaceExamples;

interface MusicInstrucment{
    void start();
    void play();
}

class Guitar implements MusicInstrucment{
    @Override
    public void start(){
        System.out.println(" start guitar");
    }
    @Override
    public void play(){
        System.out.println("Play guitar");
    }
}

class Flute implements MusicInstrucment{
    @Override
    public void start(){
        System.out.println(" start Flute");
    }
    @Override
    public void play(){
        System.out.println("Play Flute");
    }
}

public interface InterfaceManger {

    public static void main(String[] args) {
        MusicInstrucment obj=new Flute();
        obj.play();
    }
}
