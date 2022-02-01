package com.tnctech.getfit;

import com.tnctech.getfit.core.RecordParser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * Using Java NIO and Java 8 Streams, lazily read a text file and parse lines into
 * Fitness Records
 */
public class TextFileService {

    private String fileName;
    private RecordParser parser;

    public TextFileService(String fileName) {
        this.fileName = fileName;
    }

    // Do this from a Service Class...
    public Record getNextRecord() {
        if (this.fileName == null) {
            throw new IllegalStateException("fileName cannot be null");
        }
        // TODO -> This works but may fail if fileName is located else where
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
