package com.careerit.jfs.ExceptionsPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExceptionExample3 {

    public static void main(String[] args) {

        // Read data from file
        String fileName="/employees.csv";
        try{
            List<String> list= Files.readAllLines(
                   Path.of(ExceptionExample3.class.getResource(fileName).getPath()) );

            System.out.println(list.get(0));
        }catch(IOException e){
            e.printStackTrace();
        }catch(StringIndexOutOfBoundsException e){
            e.printStackTrace();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
