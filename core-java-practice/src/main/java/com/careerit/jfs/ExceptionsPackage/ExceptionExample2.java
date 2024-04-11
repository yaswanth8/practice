package com.careerit.jfs.ExceptionsPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ExceptionExample2 {
    public static void main(String[] args) {

        System.out.println("Start of main");
        List<String> list=readDataFromFile("asf.csv");

        for(String line: list){
            System.out.println(line);
        }
        System.out.println("end of main");

    }

    public static List<String> readDataFromFile(String fileName){

        List<String> list=new ArrayList<>();
        try{
        Files.readAllLines(Path.of(fileName));
        }catch(IOException e){
            System.out.println(" exp while reading file");
            System.out.println(e.getMessage());
            e.printStackTrace();

        }
        return list;
    }
}
