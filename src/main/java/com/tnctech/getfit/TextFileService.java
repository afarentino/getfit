package com.tnctech.getfit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Using Java NIO and Java 8 Streams lazily read a text file and parse lines into
 * a List of Fitness Records
 */
public class TextFileService implements RecordParser {

    private String fileName;

    public TextFileService(String fileName) {
        this.fileName = fileName;
    }

    // Do this from a Service Class...
    public Record parseNext() {
        if (this.fileName == null) {
            throw new IllegalStateException("fileName cannot be null");
        }
        Path path = Path.of(this.fileName);

        try (Stream<String> lines = Files.lines(path)) {
            lines.forEach(s -> {
                System.out.println(s);   // Just print for now
            });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
