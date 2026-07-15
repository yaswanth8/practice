package com.careerit.java.java11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Java 11 - Files.readString / Files.writeString.
 *
 * Before Java 11 you had to read all bytes and wrap them in `new String(...)`
 * with an explicit charset. These methods default to UTF-8.
 */
public class FilesReadWriteString {

    public static void main(String[] args) throws IOException {
        Path tmp = Files.createTempFile("java11-demo-", ".txt");
        Files.writeString(tmp, "hello from Java 11\nline two\n");
        String content = Files.readString(tmp);
        System.out.println("content =\n" + content);
        Files.deleteIfExists(tmp);
    }
}
