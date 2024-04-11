package com.careerit.jfs.properties.ReadingProperties;

import java.io.IOException;
import java.util.Properties;

public class ReadingProperties {
    public static void main(String[] args) {

        Properties properties=new Properties();
        properties.setProperty("greetings","hello how are you");
        try {
            properties.load(ReadingProperties.class.getResourceAsStream("/db.properties"));
            System.out.println(properties.getProperty("db.url"));
            System.out.println(properties.getProperty("greetings"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
