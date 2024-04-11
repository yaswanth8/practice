package com.careerit.jfs.properties.ReadingProperties;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class SystemProperties {

    public static void main(String[] args) {

        Properties properties=System.getProperties();

        properties.forEach((e,v)-> System.out.println(e+" : "+v));

        String dir=properties.getProperty("java.io.tmpdir");
        System.out.println(dir);

        String fileName=dir+"/newFile.txt";
        File x=new File(fileName);
        FileWriter fw= null;
        try {
            fw = new FileWriter(x);
            fw.write("hello");
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
