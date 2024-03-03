package com.careerit.jfs.ExceptionsPackage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class ExceptionExample2 {
    public static void main(String[] args) {

        String fileName="as";
        try{
    List<String> list= Files.readAllLines(Path.of(ExampleExp1.class.getResource("/"+fileName).getPath()));
        }catch (IOException e){
            System.out.println("c");
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("b");
        }catch(Exception e){
            System.out.println("a");
        }
    }
}
