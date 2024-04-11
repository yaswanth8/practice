package com.careerit.jfs.ExceptionsPackage;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ExceptionExample4 {

    public static void main(String[] args) {

        String fileName="/employees.csv";
        FileReader fr=null;
        BufferedReader br=null;
        try{
            fr=new FileReader(fileName);
            br=new BufferedReader(fr);
            String sr;
            while((sr=br.readLine())!=null){
                System.out.println(sr);
            }
        }catch(FileNotFoundException e){
            System.out.println("file not found");
            e.printStackTrace();
        }catch(IOException e){
            System.out.println("");
        }finally {

            try {
                if(br!=null) {
                    br.close();
                }
                if(fr!=null) {
                    fr.close();
                }
            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }
}
